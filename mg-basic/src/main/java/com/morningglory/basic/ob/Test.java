package com.morningglory.basic.ob;

/**
 * @Author: nsh
 * @Date: 2018/4/11
 * @Description:
 */
public class Test {


    public static void main(String[] args) {
        //创建主题对象
        ConcreteSubject sub = new ConcreteSubject();

        //创建观察者对象
        Observer observer = new ConcreteObserver();

        //注册观察者
        sub.attach(observer);

        sub.change();
    }

}