package com.morningglory.mvc.aop.executionadvicer;

import com.morningglory.mvc.aop.executionadvicer.ControllerInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2019-08-06 15:01
 * @Desc:
 */
@Component
public class ControllerPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private String execution = "execution(public * com.morningglory.mvc.controller..*.*(..))";

    @Override
    public void afterPropertiesSet() throws Exception {

        Pointcut pointcut = new AspectJExpressionPointcut();
        ((AspectJExpressionPointcut) pointcut).setExpression(execution);

        this.advisor = new DefaultPointcutAdvisor(pointcut,createAdvice());
    }

    protected Advice createAdvice() {
        return new ControllerInterceptor();
    }
}
