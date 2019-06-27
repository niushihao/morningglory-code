package com.morningglory.basic.concurrent.wait;

/**
 * @Author: nsh
 * @Date: 2018/3/27
 * @Description:
 */
public class Add {

    private String lock;

    public Add(String lock) {
        this.lock = lock;
    }

    public void add(){
        synchronized (lock){
            ValueObject.list.add("anyString");
            lock.notifyAll();
        }
    }
}