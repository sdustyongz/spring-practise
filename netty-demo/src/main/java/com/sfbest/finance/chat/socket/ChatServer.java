package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.msg.ChatReqeustMessage;
import com.sfbest.finance.chat.msg.ChatResponseMessage;
import com.sfbest.finance.chat.msg.LoginRequestMessage;
import com.sfbest.finance.chat.msg.LoginResponseMessage;
import com.sfbest.finance.chat.repositories.ChannelRepository;
import com.sfbest.finance.chat.repositories.UserRepository;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();
        new ServerBootstrap().group(group).channel(NioServerSocketChannel.class).childHandler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(4096,11,4,1,0),
                                new MsgCoDes()
                                );
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>(){
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
                                if(UserRepository.validateUser(msg.getUserName(),msg.getPasswd())){
                                    ChannelRepository.registoryChannel(msg.getUserName(), ctx.channel());
                                    ctx.writeAndFlush(new LoginResponseMessage(msg.getUserName(),true));
                                }else{
                                    ctx.writeAndFlush(new LoginResponseMessage(msg.getUserName(),false));
                                }
                            }
                        });
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ChatReqeustMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ChatReqeustMessage msg) throws Exception {
                               // msg.getTo();
                                SocketChannel channel = (SocketChannel) ChannelRepository.getChannel(msg.getTo());
                                if(channel  != null) {
                                    channel.writeAndFlush(new ChatResponseMessage(msg.getFrom(), msg.getContent()));
                                }
                            }
                        });
                    }
                }
        ).bind(8888);
    }
}
