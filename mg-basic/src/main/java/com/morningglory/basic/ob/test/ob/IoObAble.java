package com.morningglory.basic.ob.test.ob;

/**
 * @Author: nsh
 * @Date: 2018/8/16
 * @Description:
 */
public class IoObAble extends AbstractObAble{

    @Override
    public void connectHandler() {
        System.out.println("观察到连接已经建立。。。");
    }

    @Override
    public void receiveHandler() {
        System.out.println("观察到有消息到达。。。");
    }

    @Override
    public void closedHandler() {
        System.out.println("观察到关闭连接。。。");
    }
}