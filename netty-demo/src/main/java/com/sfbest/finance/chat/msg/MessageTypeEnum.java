package com.sfbest.finance.chat.msg;

import com.sfbest.finance.chat.rpc.RemoteRequestMessage;
import com.sfbest.finance.chat.rpc.RemoteResponseMessage;

public enum MessageTypeEnum {
    LOGIN_REQUEST_MESSAGE(LoginRequestMessage.class)
    ,LOGIN_RESPONSE_MESSAGE(LoginResponseMessage.class),
    CHAT_REQUEST_MESSAGE(ChatReqeustMessage.class),
    CHAT_RESPONSE_MESSAGE(ChatResponseMessage.class),
    RPC_REQUEST(RemoteRequestMessage.class),
    RPC_RESPONSE(RemoteResponseMessage.class);

    Class<? extends Message>  msgCls ;

    MessageTypeEnum(Class<? extends  Message> msgCls){
        this.msgCls = msgCls;
    }

    public Class<? extends Message> getMsgCls() {
        return msgCls;
    }
}
