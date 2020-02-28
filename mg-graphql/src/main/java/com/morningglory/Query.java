package com.morningglory;

import com.google.common.collect.Lists;
import graphql.GraphQL;
import graphql.schema.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;

/**
 * @Author: qianniu
 * @Date: 2020-02-28 13:50
 * @Desc:
 */
public class Query {

    public static HashMap<String,Object> getExample(int id){
        HashMap example = new HashMap<String,Object>();
        if(id < 1){
            return example;
        }
        example.put("id",id);
        example.put("text","example"+id);
        return example;
    }

    public static List<HashMap<String,Object>> getExamples(){
        List<HashMap<String,Object>> list = Lists.newArrayList();
        for(int i=0;i<10;i++){
            list.add(getExample(i));
        }
        return list;
    }

    public static DataFetcher exampleFetcher =
            dataFetchingEnvironment -> getExample(dataFetchingEnvironment.getArgument("id"));

    public static DataFetcher examplesFetcher = dataFetchingEnvironment -> getExamples();


    public static GraphQLObjectType exampleType = GraphQLObjectType.newObject()
            .name("Example")
            .field(field -> field.name("id").type(new GraphQLNonNull(GraphQLInt)))
            .field(field -> field.name("text").type(GraphQLString))
            .build();

    public static GraphQLObjectType queryType = GraphQLObjectType.newObject()
            .name("QueryType")
            .description("An example query type")
            .field(field -> field.name("example").argument(argument -> argument.name("id").type(GraphQLInt))
                    .type(exampleType).dataFetcher(exampleFetcher)
            ).field(field -> field.name("examples").type(new GraphQLList(exampleType)).dataFetcher(examplesFetcher))
            .build();

    public static GraphQLSchema querySchema = GraphQLSchema.newSchema().query(queryType).build();
}