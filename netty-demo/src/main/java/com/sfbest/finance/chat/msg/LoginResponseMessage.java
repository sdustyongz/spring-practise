package com.sfbest.finance.chat.msg;

import lombok.Data;

import java.io.Serializable;

import static com.sfbest.finance.chat.msg.MessageTypeEnum.LOGIN_RESPONSE_MESSAGE;

@Data
public class LoginResponseMessage extends Message implements Serializable {

    String userName;
    boolean isSucess;

    public LoginResponseMessage(String userName, boolean isSucess) {
        this.userName = userName;
        this.isSucess = isSucess;
        this.setMessageType(LOGIN_RESPONSE_MESSAGE.ordinal());
    }
}
