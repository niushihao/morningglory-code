package com.morningglory.mvc.aop.staticadvicer;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-10-15 13:44
 * @Desc:
 */
@Slf4j
public class MyStaticPointcut extends StaticMethodMatcherPointcut implements ClassFilter {

    List<String> writeList = Lists.newArrayList("com.morningglory.mvc.service.item.ItemServiceImpl.list");
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        String completeName = Joiner.on(".").skipNulls().join(className,methodName);
        for(String path : writeList){
            if(path.equals(completeName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        if(CollectionUtils.isEmpty(writeList)){
            return false;
        }

        String name = clazz.getName();
        for(String path : writeList){
            if(path.startsWith(name)){
                log.info("MyStaticPointcut matches true");
                return true;
            }
        }

        return false;
    }
}
