package com.sfbest.demo.attach;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class JpAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        inst.addTransformer(new ModifyReturnTransformer(), true);
        // retransformClasses 是 Java SE 6 里面的新方法，它跟 redefineClasses 一样，可以批量转换类定义
        inst.retransformClasses(Dog.class);
        System.out.println("我是两个参数的 Java Agent agentmain");
    }
}
