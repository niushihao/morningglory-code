package com.morningglory.basic.concurrent;

/**
 * @Author: nsh
 * @Date: 2018/3/21
 * @Description:
 */
public class MyThreadA extends Thread{
    private MyList list;

    public MyThreadA(MyList list) {
        super();
        this.list = list;
    }

    @Override
    public void run() {
        MyService service = new MyService();
        service.addServiceMethod(list,"A");
    }
}