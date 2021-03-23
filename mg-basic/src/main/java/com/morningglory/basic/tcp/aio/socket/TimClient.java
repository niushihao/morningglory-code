package com.morningglory.basic.tcp.aio.socket;

/**
 * @author qianniu
 * @date 2020/11/9 9:55 下午
 * @desc
 */
public class TimClient {

    public static void main(String[] args) {
        int port = 8000;
        String host = "127.0.0.1";
        new Thread(new AsyncTimeClientHandler(host,port),"AIO-Time-Client-Handler").start();
    }
}
