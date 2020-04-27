package com.morningglory.basic.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: qianniu
 * @Date: 2020-04-22 13:23
 * @Desc: 线程中断测试
 * 1.sleep、wait、lockInterruptibly 这些方法都可以响应中断(非守护线程)
 */
@Slf4j
public class InteruptTest {

    private static Object LOCK = new String();
    private static NamedThreadFactory factory = new NamedThreadFactory("interupt_test",false,0);

    private static ReentrantLock reentrantLockInterupt = new ReentrantLock();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static ReentrantLock reentrantConditionLock = new ReentrantLock();
    private static Condition condition = reentrantConditionLock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        // 测试[sleep]中的线程中断
        testSleepInterupt();

        // 测试[wait]中的线程中断
        testWaitInterupt();

        // 测试 [lockInterruptibly]中的线程中断
        testReentrantLockInterupt();

        // 测试 [lock]中的线程中断
        testReentrantLock();

        // 测试 [lockWithCondition]中的线程中断
        testLockWithCondition();

        // 测试 [lockPack]中的线程中断
        testLockPack();

    }

    private static void testLockPack() throws InterruptedException {
        Runnable runnable = () -> {
            log.info("测试 [lockPack] 中的线程是否会被唤醒");
            // 这里没有设置时间所以会一直阻塞
            LockSupport.park(LOCK);
            log.info("[lockPack] 中的线程被唤醒!!!");

        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();
    }

    private static void testLockWithCondition() throws InterruptedException {
        Runnable runnable = () -> {
            log.info("测试 [lockWithCondition] 中的线程是否会被唤醒");
            reentrantConditionLock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                log.info("[lockWithCondition] 中的线程被唤醒!!!");
            }

        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();
    }

    /**
     * 不会被 中断 唤醒
     */
    private static void testReentrantLock() throws InterruptedException {

        // 先让主线程占住锁
        reentrantLock.lock();

        Runnable runnable = () -> {
            log.info("测试 [reentrantLock] 中的线程是否会被唤醒");
            reentrantLock.lock();
            log.info("获取锁成功,线程的中断状态 = {}",Thread.currentThread().isInterrupted());
        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();

        // 让锁先被占用一会，确保线程执行时拿不到锁
        Thread.sleep(2000);
        reentrantLock.unlock();
    }

    /**
     * 会被 中断 唤醒
     */
    private static void testReentrantLockInterupt() throws InterruptedException {
        // 先让主线程占住锁
        reentrantLockInterupt.lock();

        Runnable runnable = () -> {
            log.info("测试 [reentrantLockInterupt] 中的线程是否会被唤醒");
            try {
                reentrantLockInterupt.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("[reentrantLockInterupt] 中的线程被唤醒!!!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();
    }


    /**
     * 测试当线程处于 [wait] 时能否被中断
     * 当线程设置成守护线程时 不能被中断
     * 当线程设置成非守护线程时 可以被中断
     */
    private static void testWaitInterupt() throws InterruptedException {
        Runnable runnable = () -> {
            log.info("测试 [wait] 中的线程是否会被唤醒");
            try {
                synchronized (LOCK){
                    LOCK.wait(10000);
                }

            } catch (InterruptedException e) {
                log.info("[wait] 中的线程被唤醒!!!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();
    }

    /**
     * 测试当线程处于 [sleep] 时能否被中断
     * 当线程设置成守护线程时 不能被中断
     * 当线程设置成非守护线程时 可以被中断
     */
    private static void testSleepInterupt() throws InterruptedException {

        Runnable runnable = () -> {
            log.info("测试 [sleep] 中的线程是否会被唤醒");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.info("[sleep] 中的线程被唤醒!!!");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();

        // 确保run方法先执行,然后在设置中断
        Thread.sleep(1000);
        thread.interrupt();

    }
}
