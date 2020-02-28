package com.morningglory.mvc.aop.staticadvicer;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
/**
 * @Author: qianniu
 * @Date: 2019-10-15 14:12
 * @Desc:
 */
@Slf4j
public class MyStaticInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("MyStaticInterceptor-begin");
        invocation.getArguments();
        Object returnValue = invocation.proceed();
        log.info("MyStaticInterceptor-end");
        return returnValue;
    }
}
