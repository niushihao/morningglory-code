package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: qianniu
 * @Date: 2020-04-28 16:24
 * @Desc: spring属性填充测试
 * @see AutowiredAnnotationBeanPostProcessor 负责@Autowired @Value两个注解的属性注入
 * 1.创建bean实例后会执行填充属性的方法
 * 2.填充属性方法逻辑就是执行 [InstantiationAwareBeanPostProcessor] 中的填充属性方法
 * 3.先找到需要被填充的属性(有上述两个注解的属性或方法
 * 4.然后找属性的值
 *  - @Value获取比较简单,通过QualifierAnnotationAutowireCandidateResolver#getSuggestedValue获取
 *  - @Autowired 通过遍历beanDefinitions,筛选出类型匹配的部分,然后创建对象(如果有多个需要@Pirmary处理)
 * 5.反射设置值
 */
@Slf4j
public class PopulateTester {

    private final static String base_package = "com.morningglory.basic.spring";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(base_package);
        log.info("ClassPathBeanDefinitionScanner end");
        context.refresh();

        TestBean4 bean = context.getBean(TestBean4.class);
        log.info("msg = {}",bean.getMsg());
    }
}
