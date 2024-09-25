package com.sfbest.finance;

public interface CheckDataKvSerializer<T> {

     String  serialize(T data);
     T deSerialiization(String str);

     String getKvSplitor();

}
