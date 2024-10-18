package com.sfbest.finance.chat.msg;

import lombok.Data;

import java.io.Serializable;

@Data
public  abstract  class Message implements Serializable {

    private int sequenceId;
    private int messageType;

    public int getMessageType() {
        for (MessageTypeEnum value : MessageTypeEnum.values()) {
            if(value.getMsgCls().equals(this.getClass())){
                return value.ordinal();
            }
        }
        return -1;
    }
}
