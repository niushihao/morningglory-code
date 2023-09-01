package com.morningglory.basic.concurrent.collection;

import java.util.concurrent.SynchronousQueue;

/**
 * @author qianniu
 * @date 2022/12/16
 * @desc
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {

        SynchronousQueue queue = new SynchronousQueue();

        Object take = queue.take();

        queue.offer(1);
    }
}
