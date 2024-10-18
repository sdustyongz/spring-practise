package com.sfbest.finance.chat.rpc;

import com.sfbest.finance.chat.msg.Message;

public class RemoteResponseMessage<T> extends Message {

    boolean sucess;

    Class<T> valueCls;
    T value;

    Throwable error;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public Class<T> getValueCls() {
        return valueCls;
    }

    public void setValueCls(Class<T> valueCls) {
        this.valueCls = valueCls;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
