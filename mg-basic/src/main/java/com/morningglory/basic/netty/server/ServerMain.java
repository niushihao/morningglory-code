package com.morningglory.basic.netty.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2020-03-23 10:26
 * @Desc:
 */
public class ServerMain {

    public static void main(String[] args) {

        RpcServer server = new RpcServer(new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS,new ArrayBlockingQueue(100)));
        server.setListenPort(7777);
        server.init();
    }
}
