package com.morningglory.basic.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * @author qianniu
 * @date 2022/2/8
 * @desc
 */
public class MyObjectMapper {

    public static ObjectMapper getMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

    public ObjectMapper getDateFormatMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat myDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        return objectMapper;
    }
}
