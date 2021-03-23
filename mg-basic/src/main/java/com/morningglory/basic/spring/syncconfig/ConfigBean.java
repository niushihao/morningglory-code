package com.morningglory.basic.spring.syncconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2020/12/21 1:50 下午
 * @desc
 */
//@Component
public class ConfigBean {

    @Value("${name}")
    private String name;

    public String getName(){
        return name;
    }
}
