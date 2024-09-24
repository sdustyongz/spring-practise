package com.sfbest.finance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class MultiThreadNioServer {

    Selector boss ;
    Selector worker;


    public MultiThreadNioServer() throws IOException {
        boss = Selector.open();
        worker = Selector.open();
    }

    public void mainProcess() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.register(boss, SelectionKey.OP_ACCEPT,null);
        ssc.bind(new InetSocketAddress(8888));
        while(true){
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey sk = iterator.next();
                iterator.remove();
                if(sk.isAcceptable()){
                   SocketChannel sc = ssc.accept();
                }
            }
        }

    }

    class Worker implements  Runnable{
        Selector selector;

        public Worker(Selector selector) {
            this.selector = selector;
        }

        public void registory(SocketChannel sc,int opt) throws ClosedChannelException {
            selector.wakeup();
            sc.register(selector,opt,null);
        }


        @Override
        public void run() {
            try{
                selector.select();
                Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    SocketChannel sc = (SocketChannel) sk.channel();
                    if(sk.isReadable()){
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
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}
