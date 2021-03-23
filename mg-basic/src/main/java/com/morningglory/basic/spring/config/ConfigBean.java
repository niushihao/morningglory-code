package com.morningglory.basic.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qianniu
 * @date 2020/7/9 10:05 下午
 * @desc
 */
@Configuration
@Slf4j
public class ConfigBean {

    @Bean
    public Bean2 bean2 (Bean1 bean){
        log.info(bean.toString());
        return new Bean2(bean);
    }


    @Bean
    public Bean1 bean1(){
        log.info("bean1");
        return new Bean1();
    }


}
