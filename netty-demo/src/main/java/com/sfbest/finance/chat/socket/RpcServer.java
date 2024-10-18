package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.rpc.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class RpcServer {

    public static void main(String[] args) {
        new ServerBootstrap().group(new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(40960,11,4,1,0),
                                new MsgCoDes()
                        );
                        ch.pipeline().addLast(new RpcRequestHandler());
                    }
                }).bind(new InetSocketAddress(8888));
    }
}
