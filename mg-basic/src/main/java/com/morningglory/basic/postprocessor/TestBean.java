package com.morningglory.basic.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: nsh
 * @Date: 2018/5/24
 * @Description:测试application和beanfactory容器中取出bean
 */
@Component("testBean")
@Scope("prototype")
public class TestBean implements ITestBean{

    public TestBean() {
        System.out.println("构造方法");
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("beanName ="+s);
        //System.out.println("BeanPostProcessor postProcessBeforeInitialization");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        //System.out.println("BeanPostProcessor postProcessAfterInitialization");

        /*if(o instanceof TestController){
            ProxySubjectHandler subjectProxy = new ProxySubjectHandler(o);

            Object proxySubject =  Proxy.newProxyInstance(o.getClass().getClassLoader(),o.getClass().getInterfaces(),subjectProxy);

            return proxySubject;
        }*/
        return o;
    }

}