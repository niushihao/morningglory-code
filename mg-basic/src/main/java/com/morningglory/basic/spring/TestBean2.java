package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2020-04-20 17:27
 * @Desc:
 */
@Component
@Slf4j
public class TestBean2 {

    public void sayHi(){
        log.info("Hi ...");
    }
}
