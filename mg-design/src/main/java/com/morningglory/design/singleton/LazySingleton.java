package com.morningglory.design.singleton;

import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/9/23 1:00 下午
 * @desc 懒汉模式 double check lock
 */
public class LazySingleton {

    private static LazySingleton INSTANCE = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance(){
        if(Objects.nonNull(INSTANCE)){
            return INSTANCE;
        }

        synchronized (INSTANCE){
            if(Objects.nonNull(INSTANCE)){
                return INSTANCE;
            }
            return new LazySingleton();
        }
    }
}
