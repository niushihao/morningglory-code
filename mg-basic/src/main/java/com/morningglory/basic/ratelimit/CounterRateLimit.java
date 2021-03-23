package com.morningglory.basic.ratelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qianniu
 * @date 2020/11/25 9:40 上午
 * @desc 普通计数限流(固定窗口)
 */
@Slf4j
public class CounterRateLimit implements RateLimit,Runnable{

    /**
     * 限流阙值
     */
    private Integer limitCount;

    /**
     * 当前通过请求数
     */
    private AtomicInteger passCount;

    /**
     * 统计时间间隔
     */
    private long period;

    private TimeUnit timeUnit;

    private ScheduledExecutorService scheduledExecutorService;

    public CounterRateLimit(Integer limitCount) {
        this(limitCount,1000,TimeUnit.MILLISECONDS);
    }

    public CounterRateLimit(Integer limitCount, long period, TimeUnit timeUnit) {
        this.limitCount = limitCount;
        this.period = period;
        this.timeUnit = timeUnit;
        passCount = new AtomicInteger(0);
        this.startResetTask();
    }

    private void startResetTask() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this, 0, period, timeUnit);
    }

    @Override
    public boolean canPass() throws BlockException {
        if (passCount.incrementAndGet() > limitCount) {
            log.error("pass fail by CounterRateLimit");
            throw new BlockException();
        }
        log.info("pass success");
        return true;
    }

    @Override
    public void run() {
        passCount.set(0);
        log.info("窗口统计重置");
    }

    public static void main(String[] args) {
        CounterRateLimit rateLimit = new CounterRateLimit(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i< 10; i++){
            Runnable runnable = () -> {
                rateLimit.canPass();
            };
            executorService.submit(runnable);
        }
    }
}
