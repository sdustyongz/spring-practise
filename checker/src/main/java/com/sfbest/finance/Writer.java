package com.sfbest.finance;

public interface Writer<T> {
    void write(T data);
    void close();
}
