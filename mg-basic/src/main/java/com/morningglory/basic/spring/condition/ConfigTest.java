package com.morningglory.basic.spring.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qianniu
 * @date 2020/7/16 8:52 下午
 * @desc
 */
public class ConfigTest {

    private final static String base_package = "com.morningglory.basic.spring.condition";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);
        Bean1 bean1 = context.getBean(Bean1.class);
    }
}
