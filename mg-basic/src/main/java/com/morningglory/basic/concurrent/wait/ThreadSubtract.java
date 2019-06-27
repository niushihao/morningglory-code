package com.morningglory.basic.concurrent.wait;

/**
 * @Author: nsh
 * @Date: 2018/3/27
 * @Description:
 */
public class ThreadSubtract extends Thread{

    private Subtract subtract;

    public ThreadSubtract(Subtract subtract) {
        this.subtract = subtract;
    }

    @Override
    public void run(){
        subtract.subtract();
    }

}