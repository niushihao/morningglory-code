package com.morningglory.basic.spring.populate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2020-04-20 21:06
 * @Desc:
 */
@Component
@Slf4j
public class TestBean3 {

    public void sayHello(){
        log.info("Hello ...");
    }
}
