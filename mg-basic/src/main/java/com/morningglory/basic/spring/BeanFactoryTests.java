package com.morningglory.basic.spring;

import com.morningglory.basic.spring.populate.TestBean1;
import com.morningglory.basic.spring.populate.TestBean2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * @Author: qianniu
 * @Date: 2020-04-03 15:54
 * @Desc:
 */
@Slf4j
public class BeanFactoryTests {

    public static void main(String[] args) {

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        RootBeanDefinition beanDefinition1 = new RootBeanDefinition(TestBean1.class);
        RootBeanDefinition beanDefinition2 = new RootBeanDefinition(TestBean2.class);
        factory.registerBeanDefinition("testBean1",beanDefinition1);
        factory.registerBeanDefinition("testBean2",beanDefinition2);

        factory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        factory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());


        TestBean1 bean = factory.getBean(TestBean1.class);
        log.info(bean.getMsg());

    }
}
