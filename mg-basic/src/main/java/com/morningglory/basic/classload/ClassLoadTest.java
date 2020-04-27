package com.morningglory.basic.classload;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-04-14 13:46
 * @Desc:
 * 类加载步骤
 * 加载 -> 验证 -> 准备 -> 解析 -> 初始化 -> 使用 -> 销毁
 * ClassLoader.loadClass 相当于做了前四步工作也叫 加载 -> 链接
 *
 * -XX:+TraceClassLoading
 *
 * 同一个类加载去不能在家同一个类两次
 */
@Slf4j
public class ClassLoadTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String ext = "java.ext.dirs";
        System.out.println("java.ext.dirs :\n" + System.getProperty(ext));
        String cp = "java.class.path";
        System.out.println("java.class.path :\n" + System.getProperty(cp));

        log.info(System.getProperty("java.class.path"));
        String path = "com.morningglory.basic.classload.Demo";
        String path1 = ClassLoader.getSystemResource("").getPath();
        log.info("path1 = {}",path1);

        /**
         * 这个class文件不是通过源码编译过去的，而是直接创建了一个.class的文件
         * 格式是不符合规范的,测试这个主要是看 ClassLoader.loadClass 是否只执行了 [加载] 的功能
         * 结果报错,说明至少还执行了[验证]
         */
        String badPath = "/Users/nsh/Desktop/Demo.class";

        Class<?> demoClass1 = ClassLoader.getSystemClassLoader().loadClass(path);
        log.info("DeclaredFields = {},classLoader = {}",demoClass1.getDeclaredFields(),demoClass1.getClassLoader());

        DemoClassLoader demoClassLoader = new DemoClassLoader();
        // 尝试加载两次,会报错
        for(int i=0; i<2; i++){
            Class<?> demoClass2 = demoClassLoader.loadClass(path,true);
            log.info("DeclaredFields = {},classLoader = {}",demoClass2.getDeclaredFields(),demoClass2.getClassLoader());
        }


    }
}
