package com.morningglory.basic.spring.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author qianniu
 * @date 2020/6/24 3:24 下午
 * @desc
 */
@Slf4j
public class AnnotationLogInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("before AnnotationLogInterceptor invoke");
        Object result = invocation.proceed();
        log.info("after AnnotationLogInterceptor invoke");
        return result;
    }
}
