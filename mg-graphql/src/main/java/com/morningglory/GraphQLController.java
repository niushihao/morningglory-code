package com.morningglory;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-02-28 14:15
 * @Desc:
 */
@RestController
public class GraphQLController {

    @RequestMapping(value = "graphql",method = RequestMethod.GET)
    public Object graphQLQuery(@RequestParam("query") String query){

        ExecutionResult execute = new GraphQL(Query.querySchema).execute(query);
        List<GraphQLError> errors = execute.getErrors();
        if(errors.size() > 0){
            return errors;
        }
        return execute.getData();
    }
}
