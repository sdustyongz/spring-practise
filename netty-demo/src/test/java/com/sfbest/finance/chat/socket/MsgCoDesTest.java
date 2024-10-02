package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.msg.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MsgCoDesTest {

    @Test
    public void testCoDes() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(4096,11,4,1,0),
                new MsgCoDes());
        //channel.writeOutbound(new LoginRequestMessage("root","123456"));

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        EventLoopGroup group = new NioEventLoopGroup();
        new MsgCoDes().encode(null,new LoginRequestMessage("zhangsan","123456"),buf);
        channel.writeInbound(buf);

    }

}