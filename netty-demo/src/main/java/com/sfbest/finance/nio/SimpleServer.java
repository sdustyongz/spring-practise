package com.sfbest.finance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class SimpleServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel  serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sscKey = serverSocketChannel.register(selector,0,null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while(true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                } else if (sk.isReadable()) {
                    System.out.println("readable");
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    int len = sc.read(buffer);
                    while (len > 0) {
                        buffer.flip();
                        System.out.println(StandardCharsets.UTF_8.decode(buffer));
                        buffer.clear();
                        len = sc.read(buffer);
                    }
                    if(len == -1){
                        sk.cancel();
                    }
                } else if(sk.isWritable()){
                   ByteBuffer buffer  = (ByteBuffer) sk.attachment();
                    SocketChannel sc = (SocketChannel) sk.channel();
                    sc.write(buffer);
                   if(!buffer.hasRemaining()){
                       sk.attach(null);
                       //sk.cancel();
                       sk.interestOps(sk.interestOps()-SelectionKey.OP_WRITE);
                   }
                }
            }
        }

    }
}
