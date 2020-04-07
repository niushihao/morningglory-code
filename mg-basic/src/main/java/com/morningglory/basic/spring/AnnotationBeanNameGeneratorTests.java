package com.morningglory.basic.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: qianniu
 * @Date: 2020-04-03 11:11
 * @Desc:
 */
@Slf4j
public class AnnotationBeanNameGeneratorTests {

    private static AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public static void main(String[] args) {
        generateBeanNameWithNamedComponent();
        generateBeanNameWithDefaultNamedComponent();
        generateBeanNameWithNamedComponentWhereTheNameIsBlank();
        generateBeanNameFromMetaComponentWithStringValue();
    }

    /**
     * 直接使用注解中的value作为beanName
     */
    public static void generateBeanNameWithNamedComponent(){
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentWithName.class);
        String beanName = beanNameGenerator.generateBeanName(bd, registry);
        log.info("generateBeanNameWithNamedComponent = {}",beanName);
    }

    /**
     *
     */
    public static void generateBeanNameWithDefaultNamedComponent(){
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(AnonymousComponent.class);
        String beanName = beanNameGenerator.generateBeanName(bd, registry);
        log.info("generateBeanNameWithDefaultNamedComponent = {}",beanName);
    }

    public static void generateBeanNameWithNamedComponentWhereTheNameIsBlank(){
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentWithBlankName.class);
        String beanName = beanNameGenerator.generateBeanName(bd, registry);
        log.info("generateBeanNameWithNamedComponentWhereTheNameIsBlank = {}",beanName);
    }

    public static void generateBeanNameFromMetaComponentWithStringValue(){
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        AnnotatedBeanDefinition bd = new AnnotatedGenericBeanDefinition(ComponentInterfaceImpl.class);
        String beanName = beanNameGenerator.generateBeanName(bd, registry);
        log.info("generateBeanNameFromMetaComponentWithStringValue = {}",beanName);
    }









    @Component("walden")
    private static class ComponentWithName {
    }

    @Component(" ")
    private static class ComponentWithBlankName {
    }

    @Component
    private static class AnonymousComponent {
    }

    @Service()
    private static class ComponentInterfaceImpl implements ComponentInterface{
    }


    private static interface ComponentInterface{};
}
