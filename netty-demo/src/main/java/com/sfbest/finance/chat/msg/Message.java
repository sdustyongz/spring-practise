package com.sfbest.finance.chat.msg;

import lombok.Data;

import java.io.Serializable;

@Data
public  abstract  class Message implements Serializable {

    private int sequenceId;
    private int messageType;

}
