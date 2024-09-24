package com.sfbest.finance;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileCheckerProcessor {

    String path;

    CheckDataConvert convert;

    String resultFilePath;
    final static int MAX_LINES = 1000000;

    public void sortToFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8.toString()));
        int lineCount = 0 ;
        String line ;
        List<CheckDataModel> list = new LinkedList<>();
        while((line = reader.readLine()) != null){
            CheckDataModel data = convert.convert(line);
            list.add(data);
            if(++lineCount > MAX_LINES){
                throw new RuntimeException("核对文件超过100W，暂不支持");
            }
        }
        Collections.sort(list);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(resultFilePath),"utf-8"));
        for (CheckDataModel checkDataModel : list) {
            writer.println();
        }
        reader.close();
        writer.close();
    }

    public void check(String path1,String path2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(path1),"utf-8"));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(path2),"utf-8"));
    }
}
