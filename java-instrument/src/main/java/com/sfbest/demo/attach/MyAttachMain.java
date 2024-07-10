package com.sfbest.demo.attach;


import com.sun.tools.attach.VirtualMachine;

public class MyAttachMain {
    public static void main(String[] args) throws Exception {
        VirtualMachine vm = VirtualMachine.attach("6284");
        try {
            vm.loadAgent("E:\\code\\github\\spring-practise\\java-instrument\\target\\java-instrument-0.0.1-SNAPSHOT-jar-with-dependencies.jar");
        } finally {
            vm.detach();
        }
    }
}
