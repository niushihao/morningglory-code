package com.morningglory.basic.spring.autoconfig;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;

import java.lang.annotation.Annotation;

/**
 * @author qianniu
 * @date 2020/6/24 2:56 下午
 * @desc
 */
public class LogAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor implements InitializingBean {

    private Class<? extends Annotation> logAnnotationType = Log.class;



    @Override
    public void afterPropertiesSet() throws Exception {
        // 生成ClassFilter 和 MethodMatcher 的过滤规则
        Pointcut pointcut = new AnnotationMatchingPointcut(null,logAnnotationType,true);
        this.advisor = new DefaultPointcutAdvisor(pointcut,createAdvice());
    }

    private Advice createAdvice() {
        return new AnnotationLogInterceptor();
    }
}
