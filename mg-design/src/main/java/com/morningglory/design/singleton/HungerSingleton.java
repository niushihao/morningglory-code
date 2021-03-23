package com.morningglory.design.singleton;

/**
 * @author qianniu
 * @date 2020/9/23 12:58 下午
 * @desc 饿汉模式
 */
public class HungerSingleton {

    private static HungerSingleton INSTANCE = new HungerSingleton();

    private HungerSingleton() {
    }

    public static HungerSingleton getInstance(){
        return INSTANCE;
    }
}
