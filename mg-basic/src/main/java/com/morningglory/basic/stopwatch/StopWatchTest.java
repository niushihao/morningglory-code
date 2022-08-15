package com.morningglory.basic.stopwatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * @Author: qianniu
 * @Date: 2019-01-30 10:19
 * @Desc:
 */
@Slf4j
public class StopWatchTest {

    public static void main(String[] args) throws InterruptedException {

        //org.springframework.util.StopWatch
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("1");
        Thread.sleep(1000);
        stopWatch.stop();

        stopWatch.start("2");
        Thread.sleep(2000);
        stopWatch.stop();

        stopWatch.start("3");
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.prettyPrint());
        log.info(stopWatch.prettyPrint());

        //commons.lang3.time.StopWatch
        org.apache.commons.lang3.time.StopWatch watch = new org.apache.commons.lang3.time.StopWatch();
        watch.start();
        Thread.sleep(1000);
        watch.stop();
        System.out.println(watch.getTime());
    }
}
