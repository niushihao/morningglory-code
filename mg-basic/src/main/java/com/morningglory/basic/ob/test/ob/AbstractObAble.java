package com.morningglory.basic.ob.test.ob;

/**
 * @Author: nsh
 * @Date: 2018/8/16
 * @Description:
 */
public  abstract class AbstractObAble implements ObAble{


    @Override
    public void connectHandler() {
        throw new RuntimeException();
    }

    @Override
    public void receiveHandler() {
        throw new RuntimeException();
    }

    @Override
    public void closedHandler() {
        throw new RuntimeException();
    }
}