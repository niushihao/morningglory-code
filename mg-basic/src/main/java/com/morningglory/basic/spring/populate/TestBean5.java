package com.morningglory.basic.spring.populate;

import com.morningglory.basic.spring.condition.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Priority;

/**
 * @author qianniu
 * @date 2021/3/23 10:37 下午
 * @desc
 */
//@Configuration
public class TestBean5 {

    @Bean
    public Bean1 testBean1(){
        return new Bean1();
    }
}
