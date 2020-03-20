package com.morningglory.schema;

import com.google.common.collect.Lists;
import graphql.schema.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;

/**
 * @Author: qianniu
 * @Date: 2020-03-03 14:15
 * @Desc:
 */
@Data
@Slf4j
public class DemoSchema implements GQLSchema{

    private GraphQLObjectType type;

    public DemoSchema() {
        type = GraphQLObjectType.newObject()
                .name("demo")
                .field(field -> field.name("id").type(new GraphQLNonNull(GraphQLInt)))
                .field(field -> field.name("text").type(GraphQLString))
                .build();
    }

    public  HashMap<String,Object> getExample(int id){
        HashMap example = new HashMap<String,Object>();
        if(id < 1){
            return example;
        }
        example.put("id",id);
        example.put("text","example"+id);
        return example;
    }

    public  List<HashMap<String,Object>> getExamples(){
        List<HashMap<String,Object>> list = Lists.newArrayList();
        for(int i=1;i<10;i++){
            list.add(getExample(i));
        }
        return list;
    }

    public  DataFetcher exampleFetcher =
            dataFetchingEnvironment -> getExample(dataFetchingEnvironment.getArgument("id"));

    public  DataFetcher examplesFetcher = dataFetchingEnvironment -> getExamples();



    public  GraphQLObjectType queryType(){
        return GraphQLObjectType.newObject()
                .name("QueryType")
                .description("An example query type")
                .field(field -> field.name("example").argument(argument -> argument.name("id").type(GraphQLInt))
                        .type(type).dataFetcher(exampleFetcher)
                ).field(field -> field.name("examples").type(new GraphQLList(type)).dataFetcher(examplesFetcher))
                .build();
    }

    @Override
    public GraphQLSchema getSchema() {
        return GraphQLSchema.newSchema().query(queryType()).build();
    }
}
