package com.morningglory.basic.singleton;

import java.io.Serializable;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:使用静态方法
 */
public class StaticSingleton implements Serializable{

    private static StaticSingleton staticSingleton = null;

    private StaticSingleton(){}

    static {
        staticSingleton = new StaticSingleton();
    }

    public static StaticSingleton getInstance(){
        return staticSingleton;
    }

    public Object readResolve(){
        System.out.println("调用此方法");
        return staticSingleton;
    }
}