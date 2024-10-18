package com.sfbest.finance.chat.rpc;

import com.sfbest.finance.chat.socket.MsgCoDes;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcFactory {



    static Map<Integer,Promise>  promiseMap  = new ConcurrentHashMap<>();

    static Map<String,ChannelFuture>  channelMap = new ConcurrentHashMap<>();

    static Channel connect(String host, int port) throws InterruptedException {
       String key = host+":"+port;
        ChannelFuture future = channelMap.get(key);
        if(future != null){
            if(future.isDone()){
                return future.channel();
            }else{
                future.sync();
                return future.channel();
            }

        }
        synchronized (RpcFactory.class){
            future = channelMap.get(key);
            if(future != null){
                if(future.isDone()){
                    return future.channel();
                }else{
                    future.sync();
                    return future.channel();
                }
            }
             future = new Bootstrap().group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());

                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(4096,11,4,1,0),
                                    new MsgCoDes()
                            );
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<RemoteResponseMessage>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, RemoteResponseMessage response) throws Exception {
                                    Promise promise =  promiseMap.get(response.getSequenceId());
                                    if(response.sucess){
                                        promise.setSuccess(response.getValue());
                                    }else{
                                        promise.setFailure(response.getError());
                                    }
                                }
                            });
                        }
                    })
                    .connect(host,port);
            channelMap.put(key,future);
        }
        if(future.isDone()){
            return future.channel();
        }else{
            future.sync();
            return future.channel();
        }
    }




    static AtomicInteger  generator = new AtomicInteger();

     public static Object getProxy(Class inerface,String host,int port){
        Object proxy =  Proxy.newProxyInstance(RpcFactory.class.getClassLoader(),new Class[]{inerface},
                new InvocationHandler(){

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RemoteRequestMessage rim = new RemoteRequestMessage();
                        rim.setMethodName(method.getName());
                        rim.setInterfaceName(inerface.getName());
                        rim.setArgs(args);
                        rim.setParameterTypes(method.getParameterTypes());
                        rim.setSequenceId(generator.incrementAndGet());
                        Channel channel = connect(host,port);
                        Promise promise = new DefaultPromise(channel.eventLoop());
                        promiseMap.put(rim.getSequenceId(),promise);
                        channel.writeAndFlush(rim);
                        return promise.get();
                    }
                });
        return proxy;
    }
}
