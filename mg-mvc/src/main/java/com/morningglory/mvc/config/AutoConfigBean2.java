package com.morningglory.mvc.config;

import com.morningglory.mvc.config.configBeanTest.Bean2;
import com.morningglory.mvc.config.configBeanTest.Bean4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: qianniu
 * @Date: 2019-09-09 22:06
 * @Desc:
 */
@Slf4j

@AutoConfigureOrder(1)
public class AutoConfigBean2 {

    public AutoConfigBean2() {
        log.info("AutoConfigBean2 init");
    }

    @Bean
    @AutoConfigureOrder(2)
    public Bean2 bean2(){
        return new Bean2();
    }

    @Bean
    @AutoConfigureOrder(1)
    public Bean4 bean4(){
        return new Bean4();
    }
}

