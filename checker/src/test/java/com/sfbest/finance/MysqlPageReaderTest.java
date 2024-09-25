package com.sfbest.finance;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Driver;
import java.util.Arrays;
import java.util.Map;

class MysqlPageReaderTest {

    @Test
    public void testRead() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        //Driver driver =
        DataSource ds = new SimpleDriverDataSource(driver,"jdbc:mysql://localhost:3306/test?socketTimeout=60000&connectTimeout=60000&useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true&allowMultiQueries=true","root","fms123456");
        Reader<Map<String,Object>> reader = new MysqlPageReader(ds,"select * from test a",1);
        Map<String,Object> map = null;
        while(( map = reader.get()) != null){
            System.out.println(map);
        }
    }

    @Test
    public void testCheckFlow() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        //Driver driver =
        DataSource ds = new SimpleDriverDataSource(driver,"jdbc:mysql://localhost:3306/test?socketTimeout=60000&connectTimeout=60000&useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true&allowMultiQueries=true","root","fms123456");
        Reader<Map<String,Object>> reader1 = new MysqlPageReader(ds,"select * from  a",1);
        Reader<Map<String,Object>> reader2 = new MysqlPageReader(ds,"select * from  b",1);
        Map<String,Object> map;
        FileDataWriter writer1 = new FileDataWriter("data1.txt",new DefaultCheckDataConvert(Arrays.asList("name"),Arrays.asList("val"),","));
        FileDataWriter writer2 = new FileDataWriter("data2.txt",new DefaultCheckDataConvert(Arrays.asList("name"),Arrays.asList("vall"),","));
        while((map = reader1.get()) != null){
            writer1.write(map);
        }
        writer1.close();
        while((map = reader2.get()) != null){
            writer2.write(map);
        }
        writer2.close();
        FileCheckerProcessor processor = new FileCheckerProcessor(".",new DefaultCheckDataConvert(Arrays.asList("id"),Arrays.asList("name"),","));
        String sort1= processor.sortToFile("data1.txt");
        String sort2 = processor.sortToFile("data2.txt");
        String[] files = processor.check(sort1,sort2);
        for (String file : files) {
            System.out.println(file);
        }
    }


}