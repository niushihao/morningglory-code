package com.morningglory.basic.ob.test.sub;

/**
 * @Author: nsh
 * @Date: 2018/8/16
 * @Description:
 */
public class IoSubjectAble extends AbstractSubjectAble{

    /**
     * 建立连接
     */
    public void connect(){

        System.out.println("server connected.");
        obList.forEach(o -> o.connectHandler());
    }

    public void receive(){

        System.out.println("server receive");
        obList.forEach(o -> o.receiveHandler());
    }

    public void closed(){

        System.out.println("server closed.");
        obList.forEach(o -> o.closedHandler());
    }
}