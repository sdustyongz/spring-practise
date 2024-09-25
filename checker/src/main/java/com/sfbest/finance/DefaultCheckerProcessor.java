package com.sfbest.finance;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DefaultCheckerProcessor {



    String fileBasePath;

    final static int MAX_LINES = 1000000;

    final static String DIFFER_DATA_SPLIT = "&&&&&$$@@&&&^^!@!";

    public DefaultCheckerProcessor(String fileBasePath) {
        this.fileBasePath = fileBasePath;
    }

    /**
     *
     * @throws IOException
     */
    public String sortToFile(String path) throws IOException {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8.toString()));

            Path resultPath = Paths.get(fileBasePath,path+"_sort.txt");
            int lineCount = 0 ;
            String line ;
            List<KvDataModel> list = new LinkedList<>();
            while((line = reader.readLine()) != null){
                KvDataModel data = KvDataModel.convert(line,DefaultCheckDataSerializer.KV_SPLIT);
                list.add(data);
                if(++lineCount > MAX_LINES){
                    throw new RuntimeException("核对文件超过100W，暂不支持");
                }
            }
            Collections.sort(list);
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(resultPath.toFile()),"utf-8"));
            for (KvDataModel checkDataModel : list) {
                writer.println(KvDataModel.formatter(checkDataModel,DefaultCheckDataSerializer.KV_SPLIT));
            }
            reader.close();
            writer.close();
            return resultPath.toString();
        }finally {
            if(reader != null){
                reader.close();
            }
        }

    }

    /**
     *
     * @param path1 需要核对的数据1
     * @param path2 需要核对的数据2
     * @return 如果成功，返回String[3]，第一个是 差异数据的文件名，第二个是 path1存在的path2不存在的文件名，
     *      第三个是path2存在path1不存在的文件名
     * @throws IOException
     */
    public String[] check(String path1,String path2) throws IOException {
        BufferedReader reader1 = null;
        BufferedReader reader2  = null;
        PrintWriter differFileWriter =null;
        PrintWriter oneMoreFileWriter = null;
        PrintWriter twoMoreFileWriter = null;
        try{
            reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(path1),"utf-8"));
            reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(path2),"utf-8"));
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            String differFileName = path1+"_differ.txt";
            String oneMoreFileName = path1+"_more.txt";
            String twoMoreFileName = path2+"_more.txt";

            differFileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(differFileName),"utf-8"));
            oneMoreFileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(oneMoreFileName),"utf-8"));
            twoMoreFileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(twoMoreFileName),"utf-8"));

            while(line1 != null && line2 != null){
                KvDataModel model1 = KvDataModel.convert(line1,DefaultCheckDataSerializer.KV_SPLIT);
                KvDataModel model2 = KvDataModel.convert(line2,DefaultCheckDataSerializer.KV_SPLIT);
                if(model1.getKey().equals(model2.getKey())){
                    if(!model1.getValue().equals(model2.getValue())){
                        //记录到差异文件
                        differFileWriter.println(line1+DIFFER_DATA_SPLIT+line2);
                    }
                    line1 = reader1.readLine();
                    line2 =reader2.readLine();
                }else{
                    if(model1.getKey().compareTo(model2.getKey()) > 0){
                        //记录到差集表
                        twoMoreFileWriter.println(line2);
                        line2 =reader2.readLine();
                        // System.out.println(log);

                    }else{
                        oneMoreFileWriter.println(line1);
                        //记录到差集表
                        line1= reader1.readLine();

                    }
                }
            }
            while(line1 != null){
                oneMoreFileWriter.write(line1);
                line1 = reader1.readLine();
            }

            while(line2 != null){
                twoMoreFileWriter.write(line2);
                line2 = reader2.readLine();
            }

            return new String[]{differFileName,oneMoreFileName,twoMoreFileName};
        }finally {
            if(reader1 != null){
                reader1.close();
            }
            if(reader2 != null){
                reader2.close();
            }
            if(differFileWriter != null){
                differFileWriter.close();
            }
            if(oneMoreFileWriter != null){
                oneMoreFileWriter.close();
            }
            if(twoMoreFileWriter != null){
                twoMoreFileWriter.close();
            }
        }

    }
}
