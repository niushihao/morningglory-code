package com.morningglory.basic.classload;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-04-14 14:06
 * @Desc:
 * 1. Class.forName方法中可以指定是否初始化类
 * 2. newInstance 实例初始化
 */
@Slf4j
public class ClassForNameTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        log.info("*******************initialize = false*********************");
        // initialize = false时不会执行类的初始化方法 <clinit>
        Class<?> demoClass = Class.forName(
                "com.morningglory.basic.classload.Demo",
                false,
                ClassLoader.getSystemClassLoader());


        log.info("*******************initialize = true*********************");
        demoClass = Class.forName("com.morningglory.basic.classload.Demo");

        log.info("******************* newInstance *********************");
        // 如果对象只有私有的构造方法，此方法会报错
        demoClass.newInstance();
    }
}
