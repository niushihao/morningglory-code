package com.morningglory.basic.spring;

import com.morningglory.basic.spring.populate.TestBean1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 09:46
 * @Desc: 测试 AnnotationConfigApplicationContext 的扫描方法
 */
@Slf4j
public class AnnotationScaner {

    private final static String base_package = "com.morningglory.basic.spring";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);

        TestBean1 bean = context.getBean(TestBean1.class);
        log.info(bean.getMsg());

        Environment environment = context.getBean(Environment.class);
        environment.getProperty("123");
    }
}
