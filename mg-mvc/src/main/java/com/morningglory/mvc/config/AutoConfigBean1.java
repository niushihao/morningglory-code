package com.morningglory.mvc.config;

import com.morningglory.mvc.config.configBeanTest.Bean1;
import com.morningglory.mvc.config.configBeanTest.Bean3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: qianniu
 * @Date: 2019-09-09 22:06
 * @Desc:
 */

@ConditionalOnBean(AutoConfigBean1.class)
@Slf4j
@AutoConfigureOrder(2)
public class AutoConfigBean1 {

    public AutoConfigBean1() {
        log.info("AutoConfigBean1 init");
    }

    @Bean
    @AutoConfigureOrder(4)
    public Bean1 bean1(){
        return new Bean1();
    }

    @Bean
    @AutoConfigureOrder(3)
    public Bean3 bean3(){
        return new Bean3();
    }
}
