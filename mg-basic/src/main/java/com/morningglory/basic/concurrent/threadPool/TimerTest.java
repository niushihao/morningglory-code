package com.morningglory.basic.concurrent.threadPool;


import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2020/8/14 2:31 下午
 * @desc Timer并不属于线程池,放在这只是和ScheduledThreadPoolExecutor做个对比
 */
@Slf4j
public class TimerTest {

    private static Timer timer = new Timer("timer");
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(4);

    public static void main(String[] args) {

        for(int i= 0; i< 100; i++){

            int delay = 5000 + i;
            //timer.schedule(new MyTimerTask(i),delay);


            scheduledThreadPoolExecutor.schedule(new MyTimerTask(i),delay, TimeUnit.MILLISECONDS);

        }
        log.info("主线程放完了数据，等待执行");
    }




    static class MyTimerTask extends TimerTask{
        private int num;

        public MyTimerTask(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("ThreadName= {} run 第:{} 个任务",Thread.currentThread().getName(),num);
        }
    }

}
