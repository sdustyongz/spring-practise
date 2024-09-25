package com.sfbest.finance.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadNioServer {

    Selector boss;

    public MultiThreadNioServer() throws IOException {
        boss = Selector.open();
    }

    public void mainProcess() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.register(boss, SelectionKey.OP_ACCEPT, null);
        ssc.bind(new InetSocketAddress(8888));
        Worker workers[] = new Worker[]{new Worker("one"), new Worker("two")};
        int i = 0;
        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    workers[i++ % 2].registory(sc, SelectionKey.OP_READ);
                }
            }
        }

    }

    class Worker implements Runnable {
        Selector selector;

        volatile boolean runing = false;
        private String name;

        public Worker(String name) {
            this.name = name;
        }

        ReentrantLock lock = new ReentrantLock();

        public void registory(SocketChannel sc, int opt) throws IOException {
            lock.lock();
            try {
                if (!runing) {
                    selector = Selector.open();
                    runing = true;
                    Thread thread = new Thread(this);
                    thread.setName("Thread-" + name);
                    thread.start();
                }
            } finally {
                lock.unlock();
            }
            sc.configureBlocking(false);
            selector.wakeup();
            sc.register(selector, opt, null);
        }


        @Override
        public void run() {
            try {
                while (true) {

                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey sk = iterator.next();
                        iterator.remove();
                        SocketChannel sc = (SocketChannel) sk.channel();
                        if (sk.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(100);
                            int len = sc.read(buffer);
                            while (len > 0) {
                                buffer.flip();
                                System.out.println(StandardCharsets.UTF_8.decode(buffer));
                                buffer.clear();
                                len = sc.read(buffer);
                            }
                            if (len == -1) {
                                sk.cancel();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        MultiThreadNioServer server = new MultiThreadNioServer();
        server.mainProcess();
    }

}
