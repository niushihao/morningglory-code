package com.morningglory.basic.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author qianniu
 * @date 2024/4/7
 * @desc
 */
@Slf4j
public class FutureTest {

    // 创建线程池
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(10,16,10L
            , TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(24));



    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);
//        scheduledExecutorService.submit()
        Future<Integer> submit = executor.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        for (int i =0; i< 10; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    log.info(Thread.currentThread().getName()+"开始获取返回值。。");
                    if (finalI == 5) {
                        Thread.currentThread().interrupt();
                    }
                    submit.get(1000,TimeUnit.MILLISECONDS);
                    submit.get(1000,TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e.getMessage());
                } catch (TimeoutException timeoutException) {
                    throw new RuntimeException(timeoutException.getMessage());
                }
                log.info(Thread.currentThread().getName()+"成功获取返回值");
            });
        }

    }

}
