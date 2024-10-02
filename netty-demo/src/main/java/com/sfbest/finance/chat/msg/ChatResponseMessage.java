package com.sfbest.finance.chat.msg;

import lombok.Data;

@Data
public class ChatResponseMessage extends  Message{

    String fromUser;
    String content;

    public ChatResponseMessage(String fromUser, String content) {
        this.fromUser = fromUser;
        this.content = content;
    }
}
