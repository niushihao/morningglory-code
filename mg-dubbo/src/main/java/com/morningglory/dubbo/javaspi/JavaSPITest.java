package com.morningglory.dubbo.javaspi;

import java.util.ServiceLoader;

/**
 * @Author: qianniu
 * @Date: 2019-05-13 19:18
 * @Desc: 在META-INF/services 文件夹下创建一个文件，名称为 Robot 的全限定名 Robot。文件内容为实现类的全限定的类名，如下：
 *          Bumblebee
 *          OptimusPrime
 *
 *          就能自动加载所有实现类
 */
public class JavaSPITest {

    public static void main(String[] args) {

        ServiceLoader<Robot> load = ServiceLoader.load(Robot.class);
        System.out.println("java spi");
        load.forEach(Robot::sayHello);
    }
}
