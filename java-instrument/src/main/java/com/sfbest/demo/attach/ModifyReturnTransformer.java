package com.sfbest.demo.attach;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ModifyReturnTransformer  implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        ClassPool classPool = ClassPool.getDefault();

        if(className.startsWith("com/sfbest/demo/attach/Dog")){
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
                        classfileBuffer));
                CtMethod[] methods = ctClass.getDeclaredMethods();
                //System.err.println(methods);

                for (CtMethod method : methods) {
                  if(method.getName().equals("say")){
                      method.setBody("return \"cat\";");
                  }
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
