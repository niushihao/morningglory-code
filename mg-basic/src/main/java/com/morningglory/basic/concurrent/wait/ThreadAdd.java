package com.morningglory.basic.concurrent.wait;

/**
 * @Author: nsh
 * @Date: 2018/3/27
 * @Description:
 */
public class ThreadAdd extends Thread{
    private Add add;

    public ThreadAdd(Add add) {
        this.add = add;
    }

    @Override
    public void run(){
        add.add();
    }
}