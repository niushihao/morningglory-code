package com.morningglory.basic.spring;

import org.springframework.beans.factory.annotation.Autowired;
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

    public String getMsg(){
        testBean2.sayHi();
        testBean3.sayHello();
        return "str";
    }
}
