package com.morningglory.basic.ratelimit;

import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author qianniu
 * @date 2020/11/25 2:32 下午
 * @desc 漏筒限流
 */
@Slf4j
public class LeakyBucketRateLimit implements RateLimit,Runnable{

    /**
     * 出口限制qps
     */
    private Integer limitSecond;
    /**
     * 漏桶队列
     */
    private BlockingQueue<Thread> leakyBucket;

    private ScheduledExecutorService scheduledExecutorService;


    public LeakyBucketRateLimit(Integer limitSecond, Integer bucketSize) {
        this.limitSecond = limitSecond;
        this.leakyBucket = new LinkedBlockingDeque<>(bucketSize);
        this.startResetTask();
    }

    private void startResetTask() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("startResetTask"));
        long interval = (1000 * 1000 * 1000) / limitSecond;
        scheduledExecutorService.scheduleAtFixedRate(this, 0, interval, TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean canPass() throws BlockException {
        if (leakyBucket.remainingCapacity() == 0) {
            throw new BlockException();
        }
        leakyBucket.offer(Thread.currentThread());
        LockSupport.park();
        return true;
    }

    @Override
    public void run() {
        Thread thread = leakyBucket.poll();
        if (Objects.nonNull(thread)) {
            LockSupport.unpark(thread);
            log.info("{} 获取令牌",thread.getName());
        }
    }

    public static void main(String[] args) {
        LeakyBucketRateLimit rateLimit = new LeakyBucketRateLimit(100,10000);
        ExecutorService executorService = Executors.newFixedThreadPool(2,
                new NamedThreadFactory("LeakyBucketRateLimit",false,10));
        for(int i = 0; i< 100; i++){
            Runnable runnable = () -> {
                rateLimit.canPass();
            };
            executorService.submit(runnable);
        }
    }
}
