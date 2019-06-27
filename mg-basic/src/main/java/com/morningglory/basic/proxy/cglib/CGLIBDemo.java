package com.morningglory.basic.proxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @Author: qianniu
 * @Date: 2019-04-11 22:23
 * @Desc:
 * --该设置用于输出jdk动态代理产生的类
 * System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
 *
 * --该设置用于输出cglib动态代理产生的类
 * System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\class");
 *
 * 不能代理final修饰的对象
 * 不能代理final修饰的方法
 * 可以代码抽象类，但是抽象方法不能被代理
 */
public  class CGLIBDemo {

    private void test1(){
        System.out.println("hello world test1");
    }

    public  void test2() {
        System.out.println("hello world test2");
    }

    public static void main(String[] args) {

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/nsh/Desktop");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBDemo.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
            System.out.println("before method run...");
            Object result = proxy.invokeSuper(obj, args1);
            System.out.println("after method run...");
            return result;
        });
        CGLIBDemo sample = (CGLIBDemo) enhancer.create();
        sample.test1();
        sample.test2();
    }
}
