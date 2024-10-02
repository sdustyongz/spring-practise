package com.sfbest.finance.chat.msg;

import lombok.Data;

@Data
public class LoginRequestMessage extends  Message{

    String userName;
    String passwd;


    public LoginRequestMessage(String userName, String passwd) {
        this.userName = userName;
        this.passwd = passwd;
        this.setMessageType(MessageTypeEnum.LOGIN_REQUEST_MESSAGE.ordinal());
    }
}
