package com.morningglory.basic.thread.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-20 09:37
 * @Desc: 可继承的Thread
 * 1.线程创建时会自动将父线程中的inheritableThreadLocals复制到子线程中
 * 2.重写了createMap方法，初始化和设置值的时候操作的都是inheritableThreadLocals属性
 * 3.重写了getMap方法，取值的时候也是从inheritableThreadLocals属性中去取
 */
@Slf4j
public class InheritableThreadLocalTest {

    static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        // 主线程设置
        threadLocal.set(1);
        log.info("ThreadName:{},value:{}",Thread.currentThread().getName(),threadLocal.get());

        Thread child = new Thread(() -> {
            log.info("ThreadName:{},value:{}",Thread.currentThread().getName(),threadLocal.get());
        });
        child.start();
    }

}
