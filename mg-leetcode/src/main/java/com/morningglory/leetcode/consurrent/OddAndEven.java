package com.morningglory.leetcode.consurrent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianniu
 * @date 2020/6/22 9:19 上午
 * @desc
 * 假设有这么一个类：
 *
 * class ZeroEvenOdd {
 *   public ZeroEvenOdd(int n) { ... }      // 构造函数
 *   public void zero(printNumber) { ... }  // 仅打印出 0
 *   public void even(printNumber) { ... }  // 仅打印出 偶数
 *   public void odd(printNumber) { ... }   // 仅打印出 奇数
 * }
 * 相同的一个 ZeroEvenOdd 类实例将会传递给三个不同的线程：
 *
 * 线程 A 将调用 zero()，它只输出 0 。
 * 线程 B 将调用 even()，它只输出偶数。
 * 线程 C 将调用 odd()，它只输出奇数。
 * 每个线程都有一个 printNumber 方法来输出一个整数。请修改给出的代码以输出整数序列 010203040506... ，其中序列的长度必须为 2n。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出："0102"
 * 说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-zero-even-odd
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class OddAndEven {

    public static void main(String[] args) {



        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
        Thread zeroTask = new Thread(() -> {
            zeroEvenOdd.zero();
        },"zeroTask");
        Thread evenTask = new Thread(() -> {
            zeroEvenOdd.even();
        },"evenTask");
        Thread oddTask = new Thread(() -> {
            zeroEvenOdd.odd();
        },"oddTask");

        zeroTask.start();
        evenTask.start();
        oddTask.start();

    }

    @Data
    public static class ZeroEvenOdd{
        int printNumner;
        Lock lock = new ReentrantLock();
        Condition zeroCondition = lock.newCondition();
        Condition evenCondition = lock.newCondition();
        Condition oddCondition = lock.newCondition();
        AtomicInteger integer = new AtomicInteger(0);

        public ZeroEvenOdd(int n){
           this.printNumner = n;
        }

        public void zero(){
            lock.lock();
            try {
                while (integer.getAndIncrement() < printNumner){
                    log.info("0");
                    if(integer.get() % 2 == 0){
                        evenCondition.signalAll();
                    }else if(integer.get() % 2 == 1){
                        oddCondition.signalAll();
                    }
                    zeroCondition.await();
                }
                evenCondition.signalAll();
                oddCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void even(){
            lock.lock();
            try {
                while (integer.get() <= printNumner){
                    if(integer.get() % 2 == 0){
                        log.info(integer.get()+"");
                    }
                    zeroCondition.signalAll();
                    evenCondition.await();
                }
                zeroCondition.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void odd(){
            lock.lock();
            try {
                while (integer.get() <= printNumner){
                    if(integer.get() % 2 == 1){
                        log.info(integer.get()+"");
                    }
                    zeroCondition.signalAll();
                    oddCondition.await();
                }
                zeroCondition.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
