package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2020-04-28 16:25
 * @Desc:
 */
@Component
@Slf4j
public class TestBean4 {

    @Value("msg")
    private String msg;

    public String getMsg(){
        return msg;
    }
}
