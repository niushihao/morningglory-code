package com.morningglory.basic.spring.lazy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2020/6/15 1:50 下午
 * @desc
 */
@Component
@Slf4j
public class Bean1{
    public Bean1() {
        log.info("Bean1 init");
    }

    @Autowired
    @Lazy
    private Bean2 bean2;

    public String getMsg(){
        return "123";
    }
}