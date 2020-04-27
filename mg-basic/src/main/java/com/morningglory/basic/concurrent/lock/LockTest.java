package com.morningglory.basic.concurrent.lock;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:
 * @Date: 2018/8/22
 * @Description:
 */
@Slf4j
public class LockTest {

    final static ReentrantLock lock = new ReentrantLock();
    final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final static Condition notFull =lock.newCondition();
    final static Condition notEmpty =lock.newCondition();
    static int num =0;


    public static void main(String[] args) throws InterruptedException {

        // 重入锁测试
        lockTest();

        // 模拟阻塞队列
        //lockWithCondition();

        // 读写锁
        //readWriteLock();
    }

    /**
     * 读写锁
     * 锁降级顺序：获取写锁 -> 获取读锁 -> 释放写锁 -> 释放读锁
     * 如果先获取写锁，在获取读锁，释放的时候先释放读锁，最后释放写锁，就不存在锁降级的情况
     */
    private static void readWriteLock() {

        // 锁降级过程
        readWriteLock.writeLock().lock();
        System.out.println("获取写锁");
        readWriteLock.readLock().lock();
        System.out.println("获取读锁");

        readWriteLock.writeLock().unlock();
        System.out.println("释放写锁");
        readWriteLock.readLock().lock();
        System.out.println("释放读锁");

        // 没有锁降级
        readWriteLock.writeLock().lock();
        System.out.println("获取写锁");
        readWriteLock.readLock().lock();
        System.out.println("获取读锁");

        readWriteLock.readLock().lock();
        System.out.println("释放读锁");
        readWriteLock.writeLock().unlock();
        System.out.println("释放写锁");
    }

    /**
     * 阻塞队列
     */
    private static void lockWithCondition() throws InterruptedException {

        PriorityQueue queue = new PriorityQueue();

        Runnable producer = getProducer(queue);

        Runnable consume = getConsume(queue);

        Thread producerThread = new Thread(producer,"producer");
        Thread consumeThread = new Thread(consume,"consume");


        consumeThread.start();
        producerThread.start();

        Thread.sleep(0);
    }


    /**
     * 重入锁测试
     */
    private static void lockTest() {


        Runnable runnable = getTask();


        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();

    }

    private static Runnable getTask() {
        return () -> {
            lock.lock();
            Condition condition = lock.newCondition();
            num++;

            try {
                Thread.sleep(30000);
                System.out.println(Thread.currentThread().getName()+"获取num ="+num+" ,执行时间= "+ LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };
    }

    private static Runnable getConsume(PriorityQueue queue) {
        return () -> {
            while (true) {
                lock.lock();
                log.info("consume 获取锁成功，开始消费数据");
                try {
                    while (queue.size() == 0) {
                        try {
                            log.info("现在队列是空的，我要等待");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue.poll();
                    log.info("成功消费一条数据队列数量为 ：{}", queue.size());
                    notFull.signal();
                } finally {
                    lock.unlock();
                }
            }

        };
    }

    private static Runnable getProducer(PriorityQueue queue) {
        return () -> {
            while (true) {
                lock.lock();
                log.info("producer 获取锁成功，开始生产数据");
                try {
                    while (queue.size() == 10) {
                        try {
                            log.info("现在队列是满的，我要停止生产");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(num++);
                    Thread.sleep(2000);
                    log.info("成功生产一条数据,队列数量为 ：{}", queue.size());
                    notEmpty.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println("############################");

            }

        };
    }

}