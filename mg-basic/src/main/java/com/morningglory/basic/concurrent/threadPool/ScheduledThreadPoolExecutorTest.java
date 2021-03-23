package com.morningglory.basic.concurrent.threadPool;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2019-04-17 14:20
 * @Desc:
 */
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {


        // 创建线程池
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

        // 创建可执行任务
        Runnable runnable = () -> {
            System.out.println("Thread name ="+Thread.currentThread().getName()+" ,time ="+System.currentTimeMillis());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 无延时执行
        executor.execute(runnable);

        // 5s后触发执行一次
        ScheduledFuture<?> begin_scheduled = executor.schedule(runnable, 5, TimeUnit.SECONDS);

        // 按固定的频率执行，不受执行时长影响，到点就执行
        executor.scheduleAtFixedRate(runnable,0,10,TimeUnit.SECONDS);

        //任务执行完后，按固定的延后时间再执行。
        executor.scheduleWithFixedDelay(runnable,0,3,TimeUnit.SECONDS);

    }
}
