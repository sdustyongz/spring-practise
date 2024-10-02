package com.sfbest.finance;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

/**
 * Hello world!

 */
public class App 
{

    public static void main( String[] args )
    {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?socketTimeout=60000&connectTimeout=60000&useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true&allowMultiQueries=true",
                        "root", "fms123456")
                .globalConfig(builder -> builder
                        .author("tiger")
                        .outputDir("/Users/zhaoyong/code/auto_generate/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.tf")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
