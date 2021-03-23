package com.morningglory.basic.spring.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author qianniu
 * @date 2020/6/24 11:22 上午
 * @desc
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public SelfBeanPostProcessor selfBeanPostProcessor(){
        return new SelfBeanPostProcessor();
    }
}
