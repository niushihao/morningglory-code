package com.morningglory.mvc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2019-08-06 14:36
 * @Desc:
 */
//@Component
@Aspect
@Slf4j
public class ControllerAop {

    @Pointcut("execution(public * com.morningglory.mvc.controller..*.*(..))")
    public void pointcut(){};

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("controller-aop-begin");
        Object proceed = joinPoint.proceed();
        log.info("controller-aop-end");
        return proceed;
    }


}
