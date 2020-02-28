package com.morningglory.basic.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: qianniu
 * @Date: 2020-01-14 13:40
 * @Desc: Thread代表是线程静态方法，t代码线程实例方法
 * t.interrupt() 设置线程为中断状态
 * t.isInterrupted() 获取线程的状态中断状态
 * t.join() 阻塞当前线程，等待t线程执行完毕(在哪个线程执行这个方法，就阻塞哪个线程)
 * t.sleep() 让t休眠指定时间，不释放资源
 * Thread.interrupt() 返回线程的中断状态，并重置中断状态
 * Thread.yield() 让运行中的线程重新进入可执行状态
 */
@Slf4j
public class InterruptTest {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {

            LockSupport.park();
            for(int i= 0; i< 10; i++){
                log.info("i: {}",i);
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("InterruptedException");
                }
            }
        });



        thread.start();
        thread.interrupt();
        LockSupport.unpark(thread);

        log.info("thread.isInterrupted:{}",thread.isInterrupted());
    }


}
