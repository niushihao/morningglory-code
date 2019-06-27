package com.morningglory.basic.proxy.jdk;

/**
 * @Author: nsh
 * @Date: 2018/4/27
 * @Description:
 */
public class SubjectImpl implements Subject {
    @Override
    public void sayHello() {

        System.out.println("say hello!");
        sayHi();
    }

    @Override
    public void sayHi() {
        System.out.println("say hi!");
    }

    @Override
    public void sayWhat() {

    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("equals invoke");
        return true;
    }
}