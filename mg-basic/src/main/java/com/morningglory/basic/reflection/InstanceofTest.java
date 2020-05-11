package com.morningglory.basic.reflection;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-04-28 11:08
 * @Desc:
 * instanceof 用来判断 此实例对象是否 指定Class本身或子类
 * isAssignableFrom 用来判断 此Class是否是指定Class本身或父类
 * 也就是 instanceof 前边是子类 后边 是父类
 * isAssignableFrom 前边是父类 后边 是子类
 */
@Slf4j
public class InstanceofTest {

    public static void main(String[] args) {

        A a = new A();
        log.info("a instanceof O = {}",(a instanceof O));
        log.info("a instanceof A = {}",(a instanceof A));
        log.info("a isAssignableFrom O = {}",a.getClass().isAssignableFrom(O.class));
        log.info("O isAssignableFrom a = {}",O.class.isAssignableFrom(a.getClass()));
        log.info("a isAssignableFrom A = {}",a.getClass().isAssignableFrom(A.class));
        log.info("A isAssignableFrom a = {}",A.class.isAssignableFrom(a.getClass()));

        B b = new B();
        log.info("b instanceof O = {}",(b instanceof O));
        log.info("b instanceof A = {}",(b instanceof A));
        log.info("b isAssignableFrom O = {}",b.getClass().isAssignableFrom(O.class));
        log.info("b isAssignableFrom A = {}",b.getClass().isAssignableFrom(A.class));
    }


    public static interface O{}

    public static class A implements O{

    }

    public static class B extends A{}
}
