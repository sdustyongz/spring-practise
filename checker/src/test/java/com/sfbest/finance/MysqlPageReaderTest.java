package com.sfbest.finance;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
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
}