package com.morningglory.mvc.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @Author: qianniu
 * @Date: 2019-02-20 20:10
 * @Desc:
 */
@Configuration
@EnableAutoConfiguration
/*@PropertySource(value = "classpath:application.properties")*/

public class ConfigBean {


    /*@Value("${ttt.name}")
    private String name;*/
    /*@Bean
    @ConfigurationProperties(prefix="ttt")
    public User getUser(){
        User user = new User();
        *//*System.out.println("name= "+name);*//*
        System.out.println(user.toString());
        return user;
    }*/

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor()

    {
        return new MethodValidationPostProcessor();
    }
}
