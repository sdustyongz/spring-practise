package com.sfbest.finance.chat.serialization;

public enum SerialEnum {

    JDK(new JdkSerialization()),
    JSON(new FastJsonSerialiaztion());

    Serialization serialization;

     SerialEnum(Serialization serialization){
        this.serialization = serialization;
    }

    public Serialization getSerialization() {
        return serialization;
    }
}
