package com.morningglory.basic.ob;

/**
 * @Author: nsh
 * @Date: 2018/4/11
 * @Description:
 */
public class ConcreteSubject extends Subject{

    private String type;

    public String getType() {
        return type;
    }


    public void change(){

        System.out.println("主题发生了变化。");

        //通知所有观察者
        this.nodifyObservers();
    }
}