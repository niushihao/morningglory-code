package com.morningglory.basic.ob;

/**
 * @Author: nsh
 * @Date: 2018/4/11
 * @Description:
 */
public class ConcreteObserver implements Observer{

    @Override
    public void update(Object o) {

        System.out.println("发生了变化。");
    }
}