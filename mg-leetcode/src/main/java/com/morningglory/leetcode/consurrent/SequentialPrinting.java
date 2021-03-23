package com.morningglory.leetcode.consurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qianniu
 * @date 2020/6/18 9:24 上午
 * @desc
 * 我们提供了一个类：
 *
 * public class Foo {
 *   public void one() { print("one"); }
 *   public void two() { print("two"); }
 *   public void three() { print("three"); }
 * }
 * 三个不同的线程将会共用一个 Foo 实例。
 *
 * 线程 A 将会调用 one() 方法
 * 线程 B 将会调用 two() 方法
 * 线程 C 将会调用 three() 方法
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * 示例 1:
 *
 * 输入: [1,2,3]
 * 输出: "onetwothree"
 * 解释:
 * 有三个线程会被异步启动。
 * 输入 [1,2,3] 表示线程 A 将会调用 one() 方法，线程 B 将会调用 two() 方法，线程 C 将会调用 three() 方法。
 * 正确的输出是 "onetwothree"。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class SequentialPrinting {

    // 作为下个线程可以运行的标识
    static AtomicInteger status = new AtomicInteger(0);
    public static void main(String[] args) {
        int[] ints = {1,3,2};
        Foo foo = new Foo();

        // 线程1可以直接运行,最后会把status加1
        Thread thread1 = new Thread(() -> {
            foo.print(ints[0]);
            status.getAndIncrement();
        },"thread1");

        // 只有status == 1 (线程1执行完)才能执行,执行后status加1
        Thread thread2 = new Thread(() -> {
            while (status.get() != 1){
                log.info("***********thread2 do nothing************");
            }
            foo.print(ints[1]);
            status.getAndIncrement();
        },"thread2");

        // 只有status == 2 (线程2执行完)才能执行
        Thread thread3 = new Thread(() -> {
            while (status.get() != 2){
                log.info("***********thread3 do nothing************");
            }
            foo.print(ints[2]);

        },"thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }



    public static class Foo{

        public void one(){
            log.info(Thread.currentThread().getName()+"- print one");
        }

        public void two(){
            log.info(Thread.currentThread().getName()+"- print two");
        }

        public void three(){
            log.info(Thread.currentThread().getName()+"- print three");
        }

        public  void print(int num){
            if(1 == num){
                one();
            }else if(2 == num){
                two();
            }else if(3 == num){
                three();
            }
        }
    }
}
