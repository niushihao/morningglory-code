package com.morningglory.basic.ob.test.ob;

/**
 * 观察者
 */
public interface ObAble {


    /**
     * 连接事件
     */
    void connectHandler();

    /**
     * 接收消息事件
     */
    void receiveHandler();

    /**
     * 关闭事件
     */
    void closedHandler();

}
