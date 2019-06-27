package com.morningglory.basic.concurrent;

/**
 * @Author: nsh
 * @Date: 2018/3/21
 * @Description:
 */
public class MyThreadB extends Thread{
    private MyList list;

    public MyThreadB(MyList list) {
        super();
        this.list = list;
    }

    @Override
    public void run() {
        MyService service = new MyService();
        service.addServiceMethod(list,"B");
    }
}