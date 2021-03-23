package com.morningglory.basic.reflection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author qianniu
 * @date 2020/7/10 11:17 上午
 * @desc 获取方法的参数名称
 * 自java8开始，可以直接通过反射得到方法的参数名。取代了之前如arg0、arg1 等无含义的参数名称。
 * 不过这样有个条件：你必须手动在编译时开启-parameters 参数，否则还是获取不到。
 * 以IDEA为例，你需要在Preferences->Build,Execution,Deployment->Compiler->java Compiler 页面添加该编译选项
 * Module: 'mg-basic'	-parameters
 */
@Slf4j
public class ParameterTest {

    public void sayHi(String name,String msg){
        log.info(name+", "+msg);
    }


    public static void main(String[] args) throws NoSuchMethodException {

        Method method = ParameterTest.class.getMethod("sayHi", String.class, String.class);
        Parameter[] parameters = method.getParameters();
        Arrays.stream(parameters).forEach(p->{
            System.out.println(p.getName()+" : "+p.getType());
        });

    }
}
