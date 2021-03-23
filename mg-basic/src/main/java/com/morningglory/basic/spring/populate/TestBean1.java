package com.morningglory.basic.spring.populate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: qianniu
 * @Date: 2020-04-20 17:26
 * @Desc:
 */
@Component
public class TestBean1 {

    @Autowired
    private TestBean2 testBean2;

    @Resource
    private TestBean3 testBean3;

    @Value("${user.name1}")
    private String userName;

    public String getMsg(){
        //testBean2.sayHi();
        testBean3.sayHello();
        return "str,"+userName;
    }
}
