package com.morningglory.basic.tcp.aio.socket;

/**
 * @author qianniu
 * @date 2020/11/9 9:28 下午
 * @desc
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8000;
        AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(asyncTimeServerHandler,"AIO-Time-Server-Handler").start();
    }
}
