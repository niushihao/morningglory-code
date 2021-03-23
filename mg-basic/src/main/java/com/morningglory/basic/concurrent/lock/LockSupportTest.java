package com.morningglory.basic.concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author qianniu
 * @date 2020/9/2 11:12 上午
 * @desc
 * 如果将线程中断，LockSupport阻塞的线程将会立即返回，且线程的中断状态为true
 * 如果等到超时时间后自动返回线程的中断状态为false
 *
 * 因为LockSupport的方法都不会抛异常,所以线程任务中可能需要自己判断中断状态，然后决定此任务是否继续执行
 * LockSupport.parkNanos 表示阻塞 指定的 纳秒
 * LockSupport.parkUntil 表示阻塞到指定的时间戳（毫秒级）
 */
@Slf4j
public class LockSupportTest {



    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1000));
            //LockSupport.parkUntil(System.currentTimeMillis() + 5000);
            log.info("run,ThreadIsInterrupted = {}",Thread.currentThread().isInterrupted());
            // TODO 根据线程中断状态决定是否继续执行
            // doSomething
        });

        thread.start();

        thread.interrupt();

    }
}
