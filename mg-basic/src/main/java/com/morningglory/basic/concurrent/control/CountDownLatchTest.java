package com.morningglory.basic.concurrent.control;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: qianniu
 * @Date: 2019-05-13 11:34
 * @Desc: 一般用作等待某些线程执行完后，在执行
 */
public class CountDownLatchTest {

    // 初始化state
    private static final CountDownLatch c = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {

            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " run");
                // state减1，就是调用release方法，判断依据是state是否为0
                c.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread[] threads = new Thread[3];
        for(int i =0; i< threads.length;i++){
            threads[i] = new Thread(runnable);
        }

        for (Thread thread:threads){
            thread.start();
        }

        // 获取资源，在所有线程countDown之前会获取资源失败(state不为0)，然后放入队列自旋或者等待被唤醒
        c.await();

        System.out.println("等他们都执行完了，我才能执行");
    }
}
