package com.morningglory.basic.spring.autoconfig;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author qianniu
 * @date 2020/6/24 2:48 下午
 * @desc
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ProxyLogConfiguration {

    @Bean(name = "logAdvisor")
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogAnnotationBeanPostProcessor logAnnotationBeanPostProcessor(){
        return new LogAnnotationBeanPostProcessor();
    }

}
