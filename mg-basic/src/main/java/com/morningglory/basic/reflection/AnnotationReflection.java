package com.morningglory.basic.reflection;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: qianniu
 * @Date: 2020-04-21 13:08
 * @Desc:
 */
@Slf4j
public class AnnotationReflection {

    public static void main(String[] args) {

        AnnotatationBean bean = new AnnotatationBean();

        // 获取类上的注解信息
        getClassAnnotationInfo(bean);
        log.info("**************getClassAnnotationInfo end****************");

        // 获取属性上的注解
        getFieldAnnotationInfo(bean);
        log.info("**************getFieldAnnotationInfo end****************");

        // 获取方法上的注解
        getMethodAnnotationInfo(bean);

    }

    private static void getMethodAnnotationInfo(AnnotatationBean bean) {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for(Method method : declaredMethods){
            getAnnotationInfo(method.getAnnotations());
        }
    }

    private static void getFieldAnnotationInfo(AnnotatationBean bean) {

        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for(Field field : declaredFields){
            Annotation[] annotations = field.getAnnotations();
            getAnnotationInfo(annotations);
        }
    }


    /**
     * 获取类上的注解信息
     * 包括
     *  注解的属性名 和 属性值
     *  注解的方法名 和 方法值
     *  注解的方法默认值
     * @param bean
     */
    public static void getClassAnnotationInfo(AnnotatationBean bean){
        // 获取类上的注解
        Annotation[] annotations = bean.getClass().getAnnotations();
        getAnnotationInfo(annotations);
    }

    /**
     * 获取注解上的信息
     * @param annotations
     */
    private static void getAnnotationInfo(Annotation[] annotations){
        for(Annotation annotation : annotations){
            Class<? extends Annotation> annoClass = annotation.annotationType();
            log.info("type name = {}",annoClass.getName());

            // 注解中的属性
            Field[] declaredFields = annoClass.getDeclaredFields();
            Arrays.stream(declaredFields).forEach(f -> {

                Object value = null;
                try {
                    value = f.get(annotation);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                log.info("fieldName = {},fieldValue ={}",f.getName(),value);

            });


            // 注解中的方法
            Method[] declaredMethods = annoClass.getDeclaredMethods();
            Arrays.stream(declaredMethods).forEach(m -> {
                Object value = null;
                Object defaultValue = null;
                try {
                    value = m.invoke(annotation);
                    defaultValue = m.getDefaultValue();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                log.info("methodName = {}, methodValue = {}, delaultValue = {}",m.getName(),value,defaultValue);
            });
        }
    }

    @Data
    @DemoAnnotation(name = "123", age = 345)
    public static class AnnotatationBean{

        @DemoAnnotation(name = "f_name", age = 678)
        private String name;

    }
}
