package com.morningglory.basic.timeWheel.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2021/11/2
 * @desc
 */
@Slf4j
public class Speed {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run(Timeout timeout) throws Exception {
                //Thread.sleep(1);
            }
        };

        log.info("开始放任务");
        for(int i =0;i<100000;i++){
            executorService.submit(() -> {
                hashedWheelTimer.newTimeout(timerTask,0,TimeUnit.MILLISECONDS);
            });
        }
        log.info("任务放完了");
        while (true){
            long size = hashedWheelTimer.pendingTimeouts();
            if(size == 0){
                log.info("任务处理完了");
                return;
            }
        }


    }
}
