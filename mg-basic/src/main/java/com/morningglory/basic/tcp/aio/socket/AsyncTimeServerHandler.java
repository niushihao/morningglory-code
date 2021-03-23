package com.morningglory.basic.tcp.aio.socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author qianniu
 * @date 2020/11/9 9:28 下午
 * @desc
 */
@Slf4j
public class AsyncTimeServerHandler implements Runnable{

    private int port;
    CountDownLatch countDownLatch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            log.info("The time server is start in port :{}",port);
        }catch (Exception e){

        }
    }

    @SneakyThrows
    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        doAccept();
        countDownLatch.await();
    }

    private void doAccept() {
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
