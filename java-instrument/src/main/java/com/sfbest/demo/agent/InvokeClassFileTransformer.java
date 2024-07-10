package com.sfbest.demo.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class InvokeClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        ClassPool classPool = ClassPool.getDefault();
        System.out.println("className:"+className);
        if(className.startsWith("com/sf")){
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
                        classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                //System.err.println(methods);

                for (CtMethod method : methods) {
                    method.addLocalVariable("st001", CtClass.longType);
                    method.insertAt(1,"st001 = System.currentTimeMillis();");
                    method.insertAfter("String m111 =\""+method.getName()+"\"; System.out.println(m111+\"耗时\"+(System.currentTimeMillis() - st001));",false);

                }
                byteCode = ctClass.toBytecode();
                ctClass.detach();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return byteCode;
    }
}
