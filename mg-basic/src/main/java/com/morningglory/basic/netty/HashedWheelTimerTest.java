package com.morningglory.basic.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2019-12-05 13:34
 * @Desc:
 */
@Slf4j
public class HashedWheelTimerTest {

    public static void main(String[] args) {

        /**
         * TickDuration：即一个格子代表的时间，默认为100ms，因为IO事件不需要那么精确
         * ticksPerWheel:一个Wheel含有多少个格子
         */
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS,10);
        timer.newTimeout(new MyTimerTask(timer),10,TimeUnit.SECONDS);
    }

    public static class MyTimerTask implements TimerTask{
        private HashedWheelTimer timer;

        public MyTimerTask(HashedWheelTimer timer) {
            this.timer = timer;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            log.info("hello world");
            timer.newTimeout(this,10,TimeUnit.SECONDS);
        }
    }
}
