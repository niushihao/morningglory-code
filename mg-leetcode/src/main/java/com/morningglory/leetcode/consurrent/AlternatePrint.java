package com.morningglory.leetcode.consurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianniu
 * @date 2020/6/19 12:52 下午
 * @desc 交替打印
 * 我们提供一个类：
 *
 * class FooBar {
 *   public void foo() {
 *     for (int i = 0; i < n; i++) {
 *       print("foo");
 *     }
 *   }
 *
 *   public void bar() {
 *     for (int i = 0; i < n; i++) {
 *       print("bar");
 *     }
 *   }
 * }
 * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-foobar-alternately
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class AlternatePrint {

    static AtomicInteger count = new AtomicInteger(0);
    static Lock lock = new ReentrantLock(false);

    public static void main(String[] args) {
        Condition fooCondition = lock.newCondition();
        Condition barCondition = lock.newCondition();

        FooBar fooBar = new FooBar();

        Thread task1 = new Thread(() -> {
            lock.lock();
            // 执行指定的次数
            while (count.get() < 10) {
                try {
                    // 如果是偶数则执行 foo方法
                    if (count.get() % 2 == 0) {
                        fooBar.foo();
                        // 执行成功后计数加一,并唤醒阻塞的 task2线程
                        count.incrementAndGet();
                        barCondition.signal();
                    } else {
                        log.info("等待 bar()方法执行");
                        fooCondition.await();
                    }
                }catch (Exception e){

                }
            }
            lock.unlock();

        },"task1");

        Thread task2 = new Thread(() -> {
            lock.lock();
            while (count.get() < 10) {
                try {
                    if (count.get() % 2 == 1) {
                        fooBar.bar();
                        count.incrementAndGet();
                        fooCondition.signal();
                    } else {
                        log.info("等待 foo()方法执行");
                        barCondition.await();
                    }
                }catch (Exception e){

                }
            }
            lock.unlock();
        },"task2");


        task1.start();
        task2.start();
    }
    public static class FooBar{
        public void foo(){
            log.info("foo");
        }

        public void bar(){
            log.info("bar");
        }
    }
}
