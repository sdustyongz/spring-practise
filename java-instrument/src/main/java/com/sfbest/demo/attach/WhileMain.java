package com.sfbest.demo.attach;

public class WhileMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Dog().say());
        int count = 0;
        Dog dog = new Dog();
        while (true) {
            // 等待0.5秒
            Thread.sleep(1000);
            count++;
            String say = new Dog().say();
            // 输出内容和次数
            System.out.println(say + count);
            // 内容不对或者次数达到1000次以上输出
            System.out.println("原来的dog"+dog.say());

        }
    }
}
