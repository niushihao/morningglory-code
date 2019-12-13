package com.morningglory.basic.util;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: qianniu
 * @Date: 2019-11-04 23:15
 * @Desc:
 */
public class MyDate {
    private static AtomicLong atomicLong = new AtomicLong(1);

    public static void main(String[] args) throws InterruptedException {

        /*System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());
        for(int i=0;i<10;i++){
            System.out.println(String.format("%010d", atomicLong.getAndIncrement()));
        }*/

        System.out.println(String.format("%04","1"));

        int i = Integer.parseInt("0x3e03", 16);
        System.out.println(i);
    }
}
