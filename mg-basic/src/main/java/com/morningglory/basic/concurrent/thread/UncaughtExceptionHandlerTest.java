package com.morningglory.basic.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/9/2 4:37 下午
 * @desc
 */
@Slf4j
public class UncaughtExceptionHandlerTest {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            int i = 1/0;
            log.info("i = {}",i);
        };
        runWithExceptionHandler(runnable);

        runWithoutExceptionHandler(runnable);
    }

    private static void runWithoutExceptionHandler(Runnable runnable) {
        Thread thread = new Thread(runnable,"runWithoutExceptionHandler");
        thread.start();
    }

    private static void runWithExceptionHandler(Runnable runnable) {
        Thread thread = new Thread(runnable,"runWithExceptionHandler");
        thread.setUncaughtExceptionHandler((t,e) -> {
            log.error("thread : {} with error : {}",t.getName(),e.getMessage(),e);
        });
        thread.start();
    }

}
