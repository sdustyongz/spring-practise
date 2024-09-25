package com.sfbest.finance;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileDataWriter<T> implements Writer<T> {

    PrintWriter writer;
    CheckDataConvert<T> dataFormatter;

    public FileDataWriter(String path, CheckDataConvert<T> dataFormatter) throws IOException {
        this.writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"utf-8"));
        this.dataFormatter = dataFormatter;
    }


    @Override
    public void write(T data) {
        writer.println(dataFormatter.serialization(data));
    }

    @Override
    public void close() {
        writer.close();
    }
}
