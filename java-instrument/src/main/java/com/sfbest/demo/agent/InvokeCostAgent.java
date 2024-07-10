package com.sfbest.demo.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class InvokeCostAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ClassFileTransformer transformer = new InvokeClassFileTransformer();
        instrumentation.addTransformer(transformer);
    }
}
