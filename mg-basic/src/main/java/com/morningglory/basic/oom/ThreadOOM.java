package com.morningglory.basic.oom;

import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2020/12/29 12:26 上午
 * @desc
 * -Xss512k
 */
public class ThreadOOM {

    public static void infiniteRun() {
        while(true) {
            Thread thread = new Thread(() -> {

                while (true) {
                    try {
                        TimeUnit.HOURS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        infiniteRun();
    }

}
