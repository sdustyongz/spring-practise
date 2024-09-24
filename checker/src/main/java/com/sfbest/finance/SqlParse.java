package com.sfbest.finance;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;

import java.util.List;

public class SqlParse {


    public static String checkMysqlAndSetLimit(String sql, int offset, int pageSize){
        List<SQLStatement> statements = SQLUtils.parseStatements(sql,"mysql");
        if(statements == null || statements.size() != 1){
            throw new RuntimeException(sql+ "解析失败");
        }
        SQLStatement statement = statements.get(0);
        if(!SQLSelectStatement.class.isAssignableFrom(statement.getClass())){
            throw new RuntimeException(sql+ "解析失败 不是SELECT");
        }
        SQLSelect select = ((SQLSelectStatement) statement).getSelect();
        if(select == null){
            throw new RuntimeException(sql+ "解析失败,select 为null");
        }
        SQLSelectQuery query =  select.getQuery();
        if(MySqlSelectQueryBlock.class.isAssignableFrom(query.getClass())){
            MySqlSelectQueryBlock mysqlQuery = (MySqlSelectQueryBlock) query;
            mysqlQuery.setLimit(setLimit(mysqlQuery.getLimit(),offset,pageSize));
        }else if(SQLUnionQuery.class.isAssignableFrom(query.getClass())){
            SQLUnionQuery unionQuery = (SQLUnionQuery) query;
            unionQuery.setLimit(setLimit(unionQuery.getLimit(),offset,pageSize));
        }else{
            throw new RuntimeException("SQL 不支持："+sql);
        }

        return statement.toString();

    }

    public static SQLLimit setLimit(SQLLimit limit ,int start,int size){
        if(limit != null){
            SQLExpr rowCount = limit.getRowCount();
            if(rowCount instanceof SQLIntegerExpr){
                Number number = ((SQLIntegerExpr)rowCount).getNumber();
                if(number.longValue() > size){
                    limit.setRowCount(size);
                }
            }else{
                throw new RuntimeException("解析SQL rowcount 失败");
            }
            SQLExpr offset = limit.getOffset();
            if(offset instanceof  SQLIntegerExpr){
                Number number = ((SQLIntegerExpr)offset).getNumber();
                if(number.longValue() < start){
                    limit.setOffset(start);
                }
            }else {
                throw new RuntimeException("解析SQL limit 失败");
            }

        }else{
            limit = new SQLLimit();
            limit.setOffset(start);
            limit.setRowCount(size);
        }
        return limit;
    }

    public static void main(String[] args) {
        String sql = "select * from abc where a=1 ";

        System.out.println(checkMysqlAndSetLimit(sql, 10, 20));
        System.out.println(checkMysqlAndSetLimit(sql, 30, 20));
        System.out.println(checkMysqlAndSetLimit(sql, 120, 20));
    }
}
