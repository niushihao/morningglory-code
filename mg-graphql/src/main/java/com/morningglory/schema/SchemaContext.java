package com.morningglory.schema;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Author: qianniu
 * @Date: 2020-03-03 16:17
 * @Desc:
 */
@Slf4j
public class SchemaContext{

    private static HashMap<String, GQLSchema> context = Maps.newHashMap();

    static {
        context.put("demo",new DemoSchema());
        context.put("student",new StudentSchema());
    }

    public static void regist(String modelKey, GQLSchema schema){
        context.put(modelKey,schema);
    }

    public static GQLSchema get(String modelKey){
        return context.get(modelKey);
    }

}
