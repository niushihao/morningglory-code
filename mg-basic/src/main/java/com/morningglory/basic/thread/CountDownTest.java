package com.morningglory.basic.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: nsh
 * @Date: 2018/7/27
 * @Description:
 */
public class CountDownTest {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch count = new CountDownLatch(3);

        Thread thread = new Thread(() -> {
            System.out.println("线程执行了。。。。");
            count.countDown();
            System.out.println("线程执行完了。。。。");
        });
        thread.start();

        System.out.println("main继续执行。。。。");
        count.await();
        System.out.println("线程和main都跑完了。。。");
    }
}