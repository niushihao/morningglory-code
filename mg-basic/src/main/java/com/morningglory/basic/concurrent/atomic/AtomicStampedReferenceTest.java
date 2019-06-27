package com.morningglory.basic.concurrent.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: qianniu
 * @Date: 2019-04-09 11:02
 * @Desc:
 */
public class AtomicStampedReferenceTest {

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(1,0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "_" + atomicStampedReference.compareAndSet(1, 2, 0, 0 + 1));
            System.out.println(Thread.currentThread().getName() + "_" + atomicStampedReference.compareAndSet(2, 1, 1, 1 + 1));
            System.out.println(Thread.currentThread().getName() + "_" + atomicStampedReference.compareAndSet(1, 2, 2, 1));
        });

        t1.start();
        t1.join();

        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());
    }
}
