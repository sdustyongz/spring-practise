package com.sfbest.finance;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileDataWriter<T> implements Writer<T> {

    PrintWriter writer;
    CheckDataKvSerializer<T> dataSerializer;

    public FileDataWriter(String path, CheckDataKvSerializer<T> dataSerializer) throws IOException {
        this.writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"utf-8"));
        this.dataSerializer = dataSerializer;
    }


    @Override
    public void write(T data) {
        writer.println(dataSerializer.serialize(data));
    }

    @Override
    public void close() {
        writer.close();
    }
}
