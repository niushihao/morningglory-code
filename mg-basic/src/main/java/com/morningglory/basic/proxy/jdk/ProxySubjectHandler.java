package com.morningglory.basic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: nsh
 * @Date: 2018/4/27
 * @Description:
 */
public class ProxySubjectHandler implements InvocationHandler{

    private Object obj;

    public ProxySubjectHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before say hello ....");

        method.invoke(obj,args);

        System.out.println("affter say hello ....");
        return null;
    }

}