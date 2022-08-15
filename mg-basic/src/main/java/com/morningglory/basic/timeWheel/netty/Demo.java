package com.morningglory.basic.timeWheel.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2021/8/31
 * @desc
 */
@Slf4j
public class Demo {

    public static void main(String[] args) {


        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();

        RetryTask retryTask = new RetryTask((() -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }), 2, 5);
        hashedWheelTimer.newTimeout(retryTask,0,TimeUnit.SECONDS);

    }


    public static class RetryTask implements TimerTask{

        private final long tick;

        private final int retries;

        private int retryTimes = 0;

        private Runnable task;

        public RetryTask(Runnable task,long tick, int retries) {
            this.task = task;
            this.tick = tick;
            this.retries = retries;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            try {
                task.run();
            }catch (Exception e){
                if(++retryTimes >= retries){
                    log.error("失败重试次数超过阈值: {}，不再重试", retries);
                }else {
                    log.error("重试失败，继续重试");
                    rePut(timeout);
                }
            }
        }

        private void rePut(Timeout timeout) {
            if(timeout == null){
                return;
            }

            if(timeout.isCancelled()){
                return;
            }

            Timer timer = timeout.timer();
            timer.newTimeout(timeout.task(),tick, TimeUnit.SECONDS);
        }
    }
}
