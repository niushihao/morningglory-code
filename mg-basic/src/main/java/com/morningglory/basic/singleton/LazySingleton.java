package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:懒汉模式
 */
public class LazySingleton {

    private static LazySingleton lazySingleton;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() throws InterruptedException {
        if(lazySingleton == null){
            Thread.sleep(300);
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}