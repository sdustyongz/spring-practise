package com.sfbest.finance.chat.rpc;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

public class RpcRequestHandler extends SimpleChannelInboundHandler<RemoteRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RemoteRequestMessage msg) throws Exception {
        RemoteResponseMessage response = new RemoteResponseMessage();

            String cls = msg.getInterfaceName()+"Impl";
            String methodName = msg.getMethodName();
            Object[]args = msg.getArgs();
            Class methodClass = Class.forName(cls);
            Method method = methodClass.getMethod(methodName,msg.getParameterTypes());
        response.setSequenceId(msg.getSequenceId());
            try{
            Object rs = method.invoke(methodClass.newInstance(),args);

                response.setSucess(true);
                response.setValueCls(rs.getClass());
                response.setValue(rs);
            }catch (Exception e){
                response.setSucess(false);
                response.setError(new RuntimeException(e.getMessage()));
            }


        ctx.writeAndFlush(response);
    }

    public static void main(String[] args) {
        System.out.println(Hello.class.getName());
    }
}
