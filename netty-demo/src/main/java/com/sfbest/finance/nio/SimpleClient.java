package com.sfbest.finance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SimpleClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8888;

        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",port));
        sc.write(StandardCharsets.UTF_8.encode("hello ,world"));
        Thread.sleep(10000);
    }
}
