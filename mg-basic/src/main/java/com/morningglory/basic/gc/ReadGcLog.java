package com.morningglory.basic.gc;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-04-15 09:45
 * @Desc: 触发GC,并理解GC日志
 * 1.-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * 新生代 10M 其中 Eden区8M
 * 老年代 10M
 *
 */
@Slf4j
public class ReadGcLog {

    public static void main(String[] args) throws InterruptedException {



        // 不需要stop the word 的gc
        //gcWithoutSTW();

        // 需要stop the word 的gc
        gcSTW();
    }

    /**
     * FullGC
     */
    private static void gcSTW() {
        byte[] array1 = new byte[4*1024*1024];
        log.info("array1");
        //array1 = null;

        byte[] array2 = new byte[2*1024*1024];
        log.info("array2");
        byte[] array3 = new byte[2*1024*1024];
        log.info("array3");
        byte[] array4 = new byte[2*1024*1024];
        log.info("array4");
        byte[] array5 = new byte[128*1024];
        log.info("array5");

        byte[] array6 = new byte[2*1024*1024];
        log.info("array6");
        byte[] array7 = new byte[2*1024*1024];
        log.info("array7");
    }

    private static void gcWithoutSTW() {
        // 先建三个 2M 大小的数组
        byte[] bytes1= new byte[1024 * 1024 * 2];
        byte[] bytes2= new byte[1024 * 1024 * 2];
        byte[] bytes3= new byte[1024 * 1024 * 2];



        // 在建一个4M的数组,这时，新生代内存不足
        byte[] bytes4= new byte[1024 * 1024 * 4];
    }
}
