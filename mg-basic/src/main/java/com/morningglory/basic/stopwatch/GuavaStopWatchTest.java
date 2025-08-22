package com.morningglory.basic.stopwatch;


import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2024/5/30
 * @desc
 */
@Slf4j
public class GuavaStopWatchTest {

    public static void main(String[] args) throws InterruptedException {

        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1800);

        long elapsed = stopwatch.elapsed(TimeUnit.SECONDS);
        log.info("elapsed = {}",elapsed);

        stopwatch.stop();

        long elapsed1 = stopwatch.elapsed(TimeUnit.SECONDS);
        log.info("elapsed1 = {}",elapsed1);


    }
}
