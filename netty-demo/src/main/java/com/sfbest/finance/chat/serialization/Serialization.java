package com.sfbest.finance.chat.serialization;

public interface Serialization {
    byte[] serial(Object obj);
    <T> T deserial(byte[]buf,Class<T> cls);
}
