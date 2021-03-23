package com.morningglory.mvc.aop;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author qianniu
 * @date 2020/9/4 9:54 上午
 * @desc
 */
public class ProxySupport {

    /**
     * 获取目标方法
     * @param joinPoint
     * @return
     */
    public static Method getMethod(JoinPoint joinPoint) {

        // 获取参数类型
        Object[] args = joinPoint.getArgs();
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if(arg != null){
                argTypes[i] = arg.getClass();
            }

        }

        // 反射获取方法
        Method method = MethodUtils.getMatchingAccessibleMethod(joinPoint.getTarget().getClass(),joinPoint.getSignature().getName(),argTypes);

        return method;
    }

    /**
     * 解析动态参数
     * @param fieldKey
     * @param method
     * @param args
     * @return
     */
    public static <T> T parseFieldKeyValue(String fieldKey, Method method, Object[] args,Class<T> classType) {
        // 获取被拦截方法参数名列表
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

        // 参数名称集合
        String[] paramNames = discoverer.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();

        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();

        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paramNames.length; i++) {
            // 取得参数值
            Object value = args != null && args.length > i ? args[i] : null;
            // 设定变量
            context.setVariable(paramNames[i], value);
        }
        return  parser.parseExpression(fieldKey).getValue(context, classType);
    }
}
