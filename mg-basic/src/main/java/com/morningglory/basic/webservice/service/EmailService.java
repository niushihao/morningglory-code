package com.morningglory.basic.webservice.service;

import com.google.common.collect.Lists;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 11:26
 * @Desc:
 */
@WebService(name ="EmailService",targetNamespace = "http://service.nsh.com")
public interface EmailService {

    @WebMethod
    @WebResult(name = "String",targetNamespace = "")
    String sendEmail(@WebParam(name = "username") String username);

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList("123","456");
        String[] strings1 = list.toArray(new String[0]);
        Object[] strings2 = list.toArray();
        Arrays.stream(strings1).forEach(x -> System.out.println(x));
        Arrays.stream(strings2).forEach(x -> System.out.println(x));
    }
}
