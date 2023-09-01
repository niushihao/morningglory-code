package com.morningglory.domo;


import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-03-03 13:12
 * @Desc:
 */
public class Main {

    public static void main(String[] args) {

        GraphQLSchema schema = new Query().getSchema();

        String query1 = "{students(page:2,size:5,name:\"john\") {id,interest,name,desc}}";
        Map<String, Object> result1 = (Map<String, Object>) new GraphQL(schema).execute(query1).getData();

//        String query2 = "{student(id:6) {id,interest,name,desc}}";
//        Map<String, Object> result2 = (Map<String, Object>) new GraphQL(schema).execute(query2).getData();
//
//        String query3 = "{student(id:6) {id,interest,name,desc},students(page:2,size:5,name:\"john\") {id,interest,name,desc}}";
//        Map<String, Object> result3 = (Map<String, Object>) new GraphQL(schema).execute(query3).getData();

        // 查询用户列表
        System.out.println(result1);
        // 查询单个用户
//        System.out.println(result2);
//        // 单个用户、跟用户列表一起查
//        System.out.println(result3);
    }
}
