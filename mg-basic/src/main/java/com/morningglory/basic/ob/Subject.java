package com.morningglory.basic.ob;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/4/11
 * @Description: 抽象主题（被观察者）
 */
public abstract class Subject {

    private String status;

    /**
     * 保存注册的观察者对象
     */
    private List<Observer> list = new ArrayList<>();


    /**
     * 注册观察者对象
     * @param observer
     */
    public void attach(Observer observer){
        list.add(observer);
        System.out.println("注册观察者。");
    }

    /**
     * 删除观察者对象
     * @param observer
     */
    public void detach(Observer observer){
        list.remove(observer);
        System.out.println("删除观察者。");
    }

    /**
     * 通知所有观察者
     */
    public void nodifyObservers(){

        for(Observer observer : list){
            observer.update(this);
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}