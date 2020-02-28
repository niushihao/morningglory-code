package com.morningglory.mvc.aop.staticadvicer;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2019-10-15 14:15
 * @Desc:
 */
@Component
@Slf4j
public class MyStaticPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

        MyStaticPointcut pointcut = new MyStaticPointcut();
        pointcut.setClassFilter(pointcut);
        this.advisor = new DefaultPointcutAdvisor(pointcut,createAdvice());
    }

    protected Advice createAdvice() {
        return new MyStaticInterceptor();
    }
}
