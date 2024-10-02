package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.msg.ChatReqeustMessage;
import com.sfbest.finance.chat.msg.ChatResponseMessage;
import com.sfbest.finance.chat.msg.LoginRequestMessage;
import com.sfbest.finance.chat.msg.LoginResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ChatClient {

    static volatile int logned = 0;

    public static void main(String[] args) {

        CountDownLatch loginCountDownLatch = new CountDownLatch(1);

        new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class).handler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new LoggingHandler());
                                ch.pipeline().addLast(new MsgCoDes());
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        //super.channelActive(ctx);


                                        new Thread(() -> {
                                            Scanner scanner = new Scanner(System.in);
                                            String userName = "";
                                            while (logned == 0){
                                                System.out.println("请输入用户名：");
                                                userName = scanner.next();
                                                System.out.println("请输入密码：");
                                                String passwd = scanner.next();
                                                LoginRequestMessage requestMessage = new LoginRequestMessage(userName, passwd);
                                                ctx.writeAndFlush(requestMessage);


                                                try {
                                                    loginCountDownLatch.await();
                                                } catch (InterruptedException e) {
                                                    throw new RuntimeException(e);
                                                }

                                            }

                                            while (true) {
                                                System.out.println("输入q,退出");
                                                System.out.println("输入send username conent,则发送content内容到username");
                                                System.out.println("-------------------------------------");
                                                scanner = new Scanner(System.in);
                                                String command[] = scanner.nextLine().split(" ");
                                                ctx.writeAndFlush(new ChatReqeustMessage(userName,command[1],command[2]));
                                            }

                                        }).start();

                                    }
                                });

                                ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginResponseMessage>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
                                        if(msg.isSucess()){
                                            logned = 1;
                                        }
                                        loginCountDownLatch.countDown();
                                    }
                                });

                                ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatResponseMessage>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {

                                    }
                                });
                            }
                        }
                ).connect(new InetSocketAddress("127.0.0.1", 8888));
    }
}
