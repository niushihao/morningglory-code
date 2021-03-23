package com.morningglory.basic.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qianniu
 * @date 2020/7/10 9:28 上午
 * @desc
 */
@Configuration
@Slf4j
public class ConfigBean1 {

    @Bean
    public Bean1 testBean1(){
        log.info("testBean1");
        return new Bean1();
    }
}
