package com.morningglory.basic.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: qianniu
 * @Date: 2020-04-22 21:12
 * @Desc: 测试线程的状态
 *
 *
 * 下边是一个 完美 的线程状态总结的文章
 * @see https://my.oschina.net/goldenshaw/blog/802620
 *
 * 1.NEW : 刚创建的初始状态
 * 2.RUNNABLE : 调用start后进入可执行状态,具体执行由操作系统调度
 * 3.BLOCKED : 等待获取[monitor lock]
 *  - 如两个线程A,B 同时访问同步代码块,A线程先获取监视器锁,此时B就处于等待获取监视器锁的状态 即BLOCKED状态
 * 4.WAITING : 等待被唤醒的状态
 *  - wait,sleep,lock,LockSupport都可以使线程进入此状态
 * 5.TIMED_WAITING 等待被唤醒的状态,但是有超时的概念
 * 6.TERMINATED 结束状态
 */

/** Thread代表是线程静态方法，t代码线程实例方法
    * t.interrupt() 设置线程为中断状态
    * t.isInterrupted() 获取线程的状态中断状态
    * t.join() 阻塞当前线程，等待t线程执行完毕(在哪个线程执行这个方法，就阻塞哪个线程)
    * t.sleep() 让t休眠指定时间，不释放资源
    * Thread.interrupt() 返回线程的中断状态，并重置中断状态
     * Thread.yield() 让运行中的线程重新进入可执行状态
*/

@Slf4j
public class ThreadStatusTest {

    private static long TIME = 100000000;
    private static String LOCK = new String();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static ReentrantLock reentrantConditionLock = new ReentrantLock();
    private static Condition condition = reentrantConditionLock.newCondition();
    private static String LOCK_SUPPORT = new String();

    private static NamedThreadFactory factory = new NamedThreadFactory("thread_status_test", false, 0);

    public static void main(String[] args) {

        // sleep后进入WAITING状态
        testSleep();

        // 两个线程访问一个同监视器锁,获得不到的进入BLOCKED状态
        testWait();

        //
        testLock();

        testLockCondition();

        testLockSupport();


        // 打印所有线程的状态
        printThreadStackTrace();

    }


    private static void testLockSupport() {
        Runnable runnable = () ->{
            LockSupport.park(LOCK_SUPPORT);
        };
        factory.newThread("testLockSupport", runnable).start();
    }

    private static void testLockCondition() {
        Runnable runnable = () -> {
            reentrantConditionLock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        factory.newThread("testLockCondition", runnable).start();
    }

    /**
     * 两个线程只有一个能拿到锁，且不会释放
     */
    private static void testLock() {

        Runnable runnable1 = () -> {
            reentrantLock.lock();
            while (true) {

            }
        };

        Runnable runnable2 = () -> {
            reentrantLock.lock();
            while (true) {

            }
        };

        factory.newThread("reentrantLock_1", runnable1).start();
        factory.newThread("reentrantLock_2", runnable2).start();
    }

    private static void testWait() {
        Runnable runnable1 = () -> {
            // 占着锁不释放
            synchronized (LOCK) {

                while (true){

                }

            }
        };

        Runnable runnable2 = () -> {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                    log.info("testWait runnable2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        factory.newThread("testWait1", runnable1).start();
        factory.newThread("testWait2", runnable2).start();
    }

    private static void testSleep() {

        Runnable runnable = () -> {
            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        factory.newThread("testSleep", runnable).start();
        log.info("testSleep run");
    }

    /**
     * 打印线程的信息
     */
    private static void printThreadStackTrace() {

        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            StringBuffer msg = new StringBuffer();
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            msg.append("\n 线程:").append(thread.getName())
                    .append("\n状态:").append(thread.getState().name()).append("\n");
            for (StackTraceElement element : stack) {
                msg.append("\t").append(element).append("\n");
            }
            log.info(msg.toString());
        }


    }
}
