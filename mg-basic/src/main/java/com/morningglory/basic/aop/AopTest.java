package com.morningglory.basic.aop;

import com.morningglory.basic.postprocessor.ITestBean;
import com.morningglory.basic.postprocessor.TestBean;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;

import java.lang.reflect.Method;

/**
 * @Author: qianniu
 * @Date: 2019-04-14 11:58
 * @Desc:
 */
public class AopTest {

    public static void main(String[] args) {


        final Object raw = new TestBean();
        ProxyFactory pf = new ProxyFactory(raw);
        pf.addAdvisor(ExposeInvocationInterceptor.ADVISOR);
        //为true 表明使用cglib代理，为false使用jdk代理，默认为false
        pf.setProxyTargetClass(false);
        pf.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("befo");
            }
        });
        pf.addAdvice(new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
                System.out.println("after");
            }
        });
        ITestBean itb = (ITestBean) pf.getProxy();
        itb.postProcessBeforeInitialization(null,null);


        //手工创建一个实例
        ITestBean aspectJService = new TestBean();
        //使用AspectJ语法 自动创建代理对象
        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(aspectJService);
        //添加切面和通知类
        aspectJProxyFactory.addAspect(CheckAop.class);
        //创建代理对象
        ITestBean proxyService = aspectJProxyFactory.getProxy();
        //进行方法调用
        proxyService.postProcessAfterInitialization(null,null);

    }
}
