package com.sfbest.finance.chat.rpc;

public class HelloImpl implements Hello{
    @Override
    public String sayHello(String name) {
        int a = 1/0;
        return "hello  " + name+a;
    }
}
