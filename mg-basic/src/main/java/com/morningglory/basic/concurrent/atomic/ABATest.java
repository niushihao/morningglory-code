package com.morningglory.basic.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: qianniu
 * @Date: 2019-04-09 09:49
 * @Desc:
 */
public class ABATest {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(1);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {

            System.out.println(Thread.currentThread() + "_" + atomicReference.compareAndSet(1, 2));
            Thread.yield();
            System.out.println(Thread.currentThread() + "_" + atomicReference.compareAndSet(2, 1));

        });
        t1.setName("t1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread() + "_" + atomicReference.compareAndSet(1, 2));
        });
        t2.setName("t2");

        t1.start();
        t1.join();

        t2.start();
        t2.join();

        System.out.println("end");
    }
}

