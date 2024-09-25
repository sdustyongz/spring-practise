package com.sfbest.finance;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class MysqlPageReader implements Reader<Map<String,Object>>{

    DataSource dataSource;
    String sql;
    int pageSize;

    List<Map<String,Object>> list = null;
    int position = 0;
    int size = 0;
    volatile  int page = 0;

    public MysqlPageReader(DataSource dataSource, String sql, int pageSize) {
        this.dataSource = dataSource;
        this.sql = sql;
        this.pageSize = pageSize;
    }

    @Override
    public synchronized Map<String,Object>  get(){
        if(list == null || position == size){
            list = query();
            if(list == null){
                return null;
            }
            page++;
            position = 0;
            size = list.size();
        }
        if(position < size){
            return list.get(position++);
        }
        return null;
    }

    @Override
    public void close() {

    }

    private List<Map<String,Object>> query(){
        JdbcTemplate template  = new JdbcTemplate(dataSource);
         int start = page*pageSize;
         String generateSql = SqlParse.checkMysqlAndSetLimit(sql,start,pageSize);
         list =  template.query(generateSql,new ColumnMapRowMapper());
        return list;
    }

}
