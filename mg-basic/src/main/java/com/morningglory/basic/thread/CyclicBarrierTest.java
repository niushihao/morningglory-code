package com.morningglory.basic.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: nsh
 * @Date: 2018/7/27
 * @Description:
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(()->{
            System.out.println("线程开始。。。");
            try {
                cyclicBarrier.await();

                System.out.println("线程继续执行。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();



        System.out.println("main执行。。。");
        cyclicBarrier.await();
        System.out.println("main执行完了。。。。");
    }
}