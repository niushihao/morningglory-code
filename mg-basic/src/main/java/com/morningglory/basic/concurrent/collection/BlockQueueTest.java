package com.morningglory.basic.concurrent.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2020/9/2 10:25 上午
 * @desc
 */
@Slf4j
public class BlockQueueTest {

    public static void main(String[] args) {

        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();

        Runnable poolWithException = () -> {
            try {
                String poll = linkedBlockingQueue.poll(10000, TimeUnit.MILLISECONDS);
                log.info("pool = {},Thread isInterrupted = {}",poll,Thread.currentThread().isInterrupted());
            } catch (InterruptedException e) {
                log.error("poolWithException:{}",e.getMessage(),e);
            }
        };
        poolWithException(poolWithException);



    }

    private static void poolWithException(Runnable poolWithException) {
        Thread thread = new Thread(poolWithException);
        thread.start();
        thread.interrupt();
    }
}
