package com.morningglory.basic.spring.abstractDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 09:46
 * @Desc: 测试 AnnotationConfigApplicationContext 的扫描方法
 */
@Slf4j
public class AnnotationScaner {

    private final static String base_package = "com.morningglory.basic.spring.abstractDemo";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);

        AbstractBean bean1 = context.getBean(AbstractBean.class);
        bean1.say();

    }
}
