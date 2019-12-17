package com.morningglory.basic.concurrent.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: qianniu
 * @Date: 2019-05-10 17:51
 * @Desc: 限制并发线程数,类似于lock,只是Semaphore初始化时指定了state的大小，每此acquire将state减1直到小于0，小于0时会放入队列
 *        release是将state加一，然后唤醒队列中的头结点。
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors
        .newFixedThreadPool(THREAD_COUNT);

    // 设置state的值为10
    private static Semaphore semaphore = new Semaphore(10);


    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 将state减1，直到小于0，小于0时将当前任务放入队列
                        semaphore.acquire();
                        System.out.println("save data");
                        // state加1，唤醒队列中的头结点
                        semaphore.release();
                    } catch (InterruptedException e) {
                    }
                }
            });
        }

        threadPool.shutdown();
    }

}
