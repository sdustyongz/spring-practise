package com.sfbest.finance.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MockBufferSplitDemo {

     public void split(ByteBuffer source){
         source.flip();
         for(int i = 0 ;i < source.limit();i++){
             byte b = source.get(i);
             if(b == '\n'){
                 int length = i+1 - source.position();
                 ByteBuffer buffer  = ByteBuffer.allocate(length);
                 for(int j = 0 ; j< length;j++){
                     buffer.put(source.get());
                 }
                 buffer.flip();
                 System.out.println(StandardCharsets.UTF_8.decode(buffer));
             }
         }
         source.compact();
     }

    public static void main(String[] args) {
        MockBufferSplitDemo d  = new MockBufferSplitDemo();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(StandardCharsets.UTF_8.encode("hello wold\nadadfaad\nsss"));
        d.split(buf);;
        buf.put(StandardCharsets.UTF_8.encode("uuu\n"));
        d.split(buf);
    }
}
