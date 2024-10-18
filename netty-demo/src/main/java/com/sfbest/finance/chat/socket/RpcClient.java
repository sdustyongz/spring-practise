package com.sfbest.finance.chat.socket;

import com.sfbest.finance.chat.rpc.Hello;
import com.sfbest.finance.chat.rpc.RpcFactory;
import io.netty.bootstrap.Bootstrap;

public class RpcClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;

        Hello hello = (Hello) RpcFactory.getProxy(Hello.class,host,port);
        String str = hello.sayHello("zhangsan");
        System.out.println(str);
    }
}
