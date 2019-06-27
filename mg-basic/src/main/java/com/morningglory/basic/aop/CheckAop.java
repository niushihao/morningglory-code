package com.morningglory.basic.aop;

import com.morningglory.basic.exception.ParameterException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 * @Author: nsh
 * @Date: 2018/2/27
 * @Description:
 */
/*@Component
@Aspect*/
public class CheckAop {

    @Pointcut("@annotation(CheckParamer)")
    public void check(){}

    @Before("check()")
    public void checkParamer(JoinPoint pjp) throws Throwable {

        System.out.println("11111111111111111111111111111");
        Object[] o =pjp.getArgs();
        Object returnObj =new Object();

        //取第一个只是暂时做法
        Object obj =o[0];
        //String str =beanValidator(obj);
        String str ="";
        if(StringUtils.isBlank(str)){
            //returnObj= pjp.proceed();

        }else{
            throw new ParameterException(str);
        }
    }


    @After("check()")
    public void afterCheckParamer(JoinPoint pjp) throws Throwable {
        System.out.println("222222222222222222222222222222");
        Object[] o =pjp.getArgs();
        Object returnObj =new Object();

        //取第一个只是暂时做法
        Object obj =o[0];
        //String str =beanValidator(obj);
        String str ="";
        if(StringUtils.isBlank(str)){
            //returnObj= pjp.proceed();

        }else{
            throw new ParameterException(str);
        }
    }

}
