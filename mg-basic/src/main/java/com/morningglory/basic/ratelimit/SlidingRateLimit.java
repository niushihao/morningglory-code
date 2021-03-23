package com.morningglory.basic.ratelimit;

import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianniu
 * @date 2020/11/25 10:04 上午
 * @desc 滑动窗口限流
 * 这个方法没有限制每个窗口的流量,如果要做的话需要在Window维护每个窗口的限流阙值（limitCount/windowSize）
 */
@Slf4j
public class SlidingRateLimit implements RateLimit,Runnable{

    /**
     * 限流阙值
     */
    private Integer limitCount;

    /**
     * 当前通过请求数
     */
    private AtomicInteger passCount;

    /**
     * 窗口数
     */
    private Integer windowSize;

    /**
     * 每个窗口时间间隔大小
     */
    private long windowPeriod;

    private volatile Integer windowIndex = 0;
    private Window[] windows;

    private TimeUnit timeUnit;

    private ScheduledExecutorService scheduledExecutorService;

    private Lock lock = new ReentrantLock();

    public SlidingRateLimit(Integer limitCount,Integer windowSize) {
        this(limitCount,windowSize,1000/windowSize,TimeUnit.MILLISECONDS);
    }

    public SlidingRateLimit(Integer limitCount, Integer windowSize, long windowPeriod, TimeUnit timeUnit) {
        this.passCount = new AtomicInteger(0);
        this.limitCount = limitCount;
        this.windowSize = windowSize;
        this.windowPeriod = windowPeriod;
        this.timeUnit = timeUnit;
        this.initWindows(windowSize);
        this.startResetTask();
    }

    private void startResetTask() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("startResetTask",false,1));
        log.info("startResetTask windowPeriod={}",windowPeriod);
        scheduledExecutorService.scheduleAtFixedRate(this, 1000, windowPeriod, timeUnit);
    }


    /**
     * 这个方法是否应该加锁？
     * 如果不加需要使用passCount.incrementAndGet()，那么即使失败passCount也会加1，统计不准确
     * 如果加锁 是不是对性能有影响
     * @return
     * @throws BlockException
     */
    @Override
    public boolean canPass() throws BlockException {
        // 不加锁，但是会导致passCount统计不准
//        if (passCount.incrementAndGet() > limitCount) {
//            log.error("pass fail by SlidingRateLimit，passCount = {}",passCount.get());
//            throw new BlockException();
//        }
//        windows[windowIndex].passCount.incrementAndGet();
//        log.info("pass success");
//        return true;

        // 加锁模式
        lock.lock();
        if(passCount.get() >= limitCount){
            //log.error("pass fail by SlidingRateLimit, passCount = {}",passCount.get());
            throw new BlockException();
        }
        windows[windowIndex].passCount.incrementAndGet();
        passCount.incrementAndGet();
        log.info("pass success");
        lock.unlock();
        return true;
    }

    @Override
    public void run() {
        // 获取当前窗口索引
        int curIndex = (windowIndex+1) % windowSize;
        log.info("info_reset_task, curIndex = {}", curIndex);

        // 重置当前窗口通过数量,并获取上次通过数量
        int count = windows[curIndex].passCount.getAndSet(0);
        windowIndex = curIndex;

        // 总通过数量 减去 当前窗口上次通过数量
        passCount.addAndGet(-count);
        log.info("info_reset_task, curOldCount = {}, passCount = {}", count, passCount);

    }

    public static void main(String[] args) throws InterruptedException {
        SlidingRateLimit rateLimit = new SlidingRateLimit(5,5);

        Thread.sleep(2000);
        ExecutorService executorService = Executors.newFixedThreadPool(2,new NamedThreadFactory("SlidingRateLimit",false,10));
        for(int i = 0; i< 100; i++){
            Runnable runnable = () -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rateLimit.canPass();

            };
            executorService.submit(runnable);
        }
    }


    public class Window{
        private AtomicInteger passCount;
        public Window() {
            this.passCount = new AtomicInteger(0);
        }
    }

    /**
     * 初始化窗口
     * @param windowSize
     */
    private void initWindows(Integer windowSize) {
        windows = new Window[windowSize];
        for(int i=0; i< windowSize;i++){
            windows[i] = new Window();
        }
    }
}
