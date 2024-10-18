package com.sfbest.finance.chat.serialization;

import java.io.*;

public class JdkSerialization implements  Serialization{

    @Override
    public byte[] serial(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T deserial(byte[] buf,Class<T> cls) {
        try{
            ByteArrayInputStream  bis = new ByteArrayInputStream(buf);
            ObjectInputStream in = new ObjectInputStream(bis);
            return (T)in.readObject();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
