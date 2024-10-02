package com.sfbest.finance.chat.msg;

import lombok.Data;

@Data
public class ChatReqeustMessage extends Message{

    private String from;
    private String to;
    private String content;

    public ChatReqeustMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.setMessageType(MessageTypeEnum.CHAT_REQUEST_MESSAGE.ordinal());
    }


}
