package com.morningglory.schema;

import com.morningglory.fetcher.StudentDataFetcher;
import com.morningglory.model.Student;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;
import java.util.ArrayList;
import java.util.List;
import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLLong;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @Author: qianniu
 * @Date: 2020-03-03 14:15
 * @Desc:
 */
public class StudentSchema implements GQLSchema {

    private GraphQLOutputType type;

    public StudentSchema() {
        type = newObject()
                .name("student")
                .field(newFieldDefinition().name("id").type(GraphQLLong).build())
                .field(newFieldDefinition().name("age").type(GraphQLInt).build())
                .field(newFieldDefinition().name("interest").type(GraphQLString).build())
                .field(newFieldDefinition().name("name").type(GraphQLString).build())
                .field(newFieldDefinition().name("desc").type(GraphQLString).build())
                .build();
    }

    private GraphQLFieldDefinition createUserField() {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("getStudentById")
                .argument(newArgument().name("id").type(GraphQLInt).build())
                .type(type)
                .dataFetcher(new StudentDataFetcher())
                .build();
    }

    private GraphQLFieldDefinition createUsersField() {
        return GraphQLFieldDefinition.newFieldDefinition()
                .name("pageStudents")
                .argument(newArgument().name("page").type(GraphQLInt).build())
                .argument(newArgument().name("size").type(GraphQLInt).build())
                .argument(newArgument().name("name").type(GraphQLString).build())
                .type(new GraphQLList(type))
                .dataFetcher(environment -> {
                    // 获取查询参数
                    int page = environment.getArgument("page");
                    int size = environment.getArgument("size");
                    String name = environment.getArgument("name");

                    // 执行查询, 这里随便用一些测试数据来说明问题
                    List<Student> list = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        Student user = new Student();
                        user.setId(Long.parseLong(String.valueOf(i)));
                        user.setAge(10);
                        user.setInterest("🏀");
                        user.setName("Name_" +i);
                        user.setDesc("pic_" + i +".jpg");
                        list.add(user);
                    }
                    return list;
                })
                .build();
    }

    @Override
    public GraphQLSchema getSchema() {
        GraphQLSchema schema = GraphQLSchema.newSchema().query(newObject()
                .name("GraphQuery")
                .field(createUsersField())
                .field(createUserField())
                .build()).build();
        return schema;
    }
}
