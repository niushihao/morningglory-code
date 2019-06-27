package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description: 饿汉模式，线程安全的单利模式
 */
public class HungrySingleton {

    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance() throws InterruptedException {
        return instance;
    }


}