package com.morningglory.basic.spring.abstractDemo;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2021/3/22 9:34 下午
 * @desc 抽象类不能被Spring初始化,因为在扫描BeanDefinition时被过滤掉了,也就是抽象类的BeanDefinition都扫不进去
 * ClassPathScanningCandidateComponentProvider#isCandidateComponent(org.springframework.core.type.classreading.MetadataReader)
 */
@Component

public abstract class AbstractBean {

    public void say(){
        System.out.println("Hi");
    }

    public AbstractBean() {
    }

    public AbstractBean coid(){
        return new AbstractBean() {
        };
    }
}
