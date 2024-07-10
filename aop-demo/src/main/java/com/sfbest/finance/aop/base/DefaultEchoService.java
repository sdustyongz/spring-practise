package com.sfbest.finance.aop.base;

public class DefaultEchoService implements  EchoService{
    @Override
    public String echo(String msg) {
        return msg;
    }
}
