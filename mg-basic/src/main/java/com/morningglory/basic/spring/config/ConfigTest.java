package com.morningglory.basic.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author qianniu
 * @date 2020/7/9 10:04 下午
 * @desc
 */
@Slf4j
public class ConfigTest {

    private final static String base_package = "com.morningglory.basic.spring.config";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);

        //TestBean2 bean = context.getBean(TestBean2.class);
        //Bean3 bean3 = context.getBean(Bean3.class);

        //bean.sayHi();
        Environment environment = context.getBean(Environment.class);
        environment.getProperty("123");
    }
}
