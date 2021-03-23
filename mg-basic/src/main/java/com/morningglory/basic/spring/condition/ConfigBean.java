package com.morningglory.basic.spring.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qianniu
 * @date 2020/7/16 8:50 下午
 * @desc
 */
//@Configuration
@Slf4j
public class ConfigBean {

    public ConfigBean() {
        log.info("ConfigBean init");
    }

    @ConditionalOnBean(value = Bean2.class)

    @Bean
    public Bean1 bean(){
        return new Bean1();
    }
}
