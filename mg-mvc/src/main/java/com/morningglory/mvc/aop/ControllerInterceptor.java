package com.morningglory.mvc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author: qianniu
 * @Date: 2019-08-06 15:33
 * @Desc:
 */
@Slf4j
public class ControllerInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("ControllerInterceptor-begin");
        Object returnValue = invocation.proceed();
        log.info("ControllerInterceptor-end");
        return returnValue;
    }
}
