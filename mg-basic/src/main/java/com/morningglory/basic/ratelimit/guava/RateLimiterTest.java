package com.morningglory.basic.ratelimit.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;

/**
 * @author qianniu
 * @date 2024/5/21
 * @desc
 */
public class RateLimiterTest {

    public static void main(String[] args) {
        //testAcquire();

        tryAcquire();

        //LocalDateTime.now().toEpochSecond(1);
    }

    public static void testAcquire() {
        RateLimiter limiter = RateLimiter.create(0.1);
        for(int i = 1; i < 10; i = i + 2 ) {
            double waitTime = limiter.acquire(i);
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
        }
    }

    public static void tryAcquire() {
        RateLimiter limiter = RateLimiter.create(1);
        for(int i = 1; i < 10; i = i + 2 ) {
            boolean flag =  limiter.tryAcquire();
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + flag);
        }
    }
}
