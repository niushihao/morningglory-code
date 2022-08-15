package com.morningglory.basic.timeWheel;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2021/8/16
 * @desc
 */
@Slf4j
public class Test {

    public static void main(String[] args) {

        DelayQueue delayeds = new DelayQueue<>();
        LinkedList<Object> objects = new LinkedList<>();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                log.info("delayeds begin ...");
                for (int i = 0; i < 100000; i++) {
                    delayeds.add(new Task1());
                    delayeds.add(new Task1());
                    delayeds.add(new Task1());
                    delayeds.poll();
                }
                log.info("delayeds end ...");
            }


        };

        Runnable runnable2 = new Runnable() {

            @Override
            public void run() {
                log.info("objects begin ...");
                for (int i = 0; i < 100000; i++) {
                    objects.add(new Task1());
                    objects.add(new Task1());
                    objects.add(new Task1());
                    objects.poll();
                }
                log.info("objects end ...");
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }


    static class Task1 implements Delayed{

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }
}
