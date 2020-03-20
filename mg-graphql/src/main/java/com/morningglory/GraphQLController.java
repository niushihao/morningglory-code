package com.morningglory;
import com.morningglory.schema.GQLSchema;
import com.morningglory.schema.SchemaContext;
import com.morningglory.schema.StudentSchema;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-02-28 14:15
 * @Desc:
 */
@RestController
public class GraphQLController {

    @RequestMapping(value = "graphql/{modelKey}",method = RequestMethod.GET)
    public Object graphQLQuery(@PathVariable String modelKey,@RequestParam("query") String query){

        GQLSchema gqlSchema = SchemaContext.get(modelKey);
        GraphQLSchema schema = gqlSchema.getSchema();
        ExecutionResult execute = new GraphQL(schema).execute(query);
        List<GraphQLError> errors = execute.getErrors();
        if(errors.size() > 0){
            return errors;
        }
        return execute.getData();
    }

}
