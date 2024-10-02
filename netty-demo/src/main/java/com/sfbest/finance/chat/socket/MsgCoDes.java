package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.msg.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
public class MsgCoDes extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, ByteBuf byteBuf) throws Exception {
         //模数 abcd
         byteBuf.writeBytes(new byte[]{65,66,67,68});
         //版本号
         byteBuf.writeByte(1);
         //序列化方式
         byteBuf.writeByte(0);
         //消息类型
        byteBuf.writeByte(msg.getMessageType());
        byteBuf.writeInt(msg.getSequenceId());

        ByteArrayOutputStream  bos = new ByteArrayOutputStream(1024);
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(msg);
        byte[]data = bos.toByteArray();
        byteBuf.writeInt(data.length);
        byteBuf.writeByte(0);
        byteBuf.writeBytes(data);
        log.info("length:{},data:{}",data.length,data);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        int magic = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte seriallizeType = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        int seqId = byteBuf.readInt();
        int len = byteBuf.readInt();
        byteBuf.readByte();
        byte []data = new byte[len];
        for(int i = 0 ;i< len;i++){
            data[i] = byteBuf.readByte();
        }
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
        Message msg = (Message) in.readObject();
        log.info("收到messgae：{}",msg);
        list.add(msg);
    }
}
