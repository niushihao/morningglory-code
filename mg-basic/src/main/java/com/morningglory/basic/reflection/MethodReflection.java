package com.morningglory.basic.reflection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @Author: qianniu
 * @Date: 2020-04-21 15:29
 * @Desc:
 */
@Slf4j
public class MethodReflection {

    public static void main(String[] args) throws Exception{

        // 反射调用静态方法
        callStaticMethod();



    }

    /**
     * 反射调用静态方法
     * 因为是静态方法所以 目标对象设置不设置都可以
     * @throws Exception
     */
    private static void callStaticMethod() throws Exception{
        MethodReflection methodReflection = new MethodReflection();
        Method method = MethodReflection.class.getMethod("getStr");
        Object result = method.invoke(methodReflection);
        log.info("result = {}",result);

        result = method.invoke(null);
        log.info("result = {}",result);
    }

    public static String getStr(){
        return "str";
    }
}
