package com.morningglory.basic.concurrent.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author qianniu
 * @date 2024/3/7
 * @desc
 */
@Slf4j
public class RejectTest {

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(2,2,0, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(1));

    public static ThreadPoolExecutor
            COMMON_EXECUTOR = new ThreadPoolExecutor(10, 20, 10L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

    public static void main(String[] args) throws InterruptedException {

        /**
         * 队列小于一次提交的任务数时，如果主线程一直占用队列的锁，导致核心线程无法获取任务 从而触发拒绝
         */
        rejectTest();

        blockTest();
    }

    private static void blockTest() {


        List<List<String>> parentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> childList = new ArrayList<>();
            parentList.add(childList);
            for (int j = 0; j < 2; j++) {
                childList.add(i + ":" + j);
            }
        }
        parentList.get(9).add("end");

        Long begin = System.currentTimeMillis();
        dealParentList(parentList);
        Long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - begin));

    }

    private static void rejectTest() throws InterruptedException {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 使用 parallelStream 进行并行处理
        numbers.parallelStream().forEach(number -> {
            System.out.println("Processing number: " + number + " on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这里可以执行一些耗时的操作
        });

        // parallelStream 的 forEach 是一个终端操作，会阻塞主线程直到处理完毕
        System.out.println("All numbers processed!");

        // 主线程会在所有元素处理完毕后继续执行

        for (int i =0; i<10;i++) {
            Thread.sleep(10000);
            for (int j =0;j<3;j++) {
                executor.execute(() -> System.out.println("11"));
            }
            System.out.println("=============请求任务");
        }

        System.out.println("任务都执行完了");
    }





    public static void dealParentList(List<List<String>> parentList) {
        for (List<String> childList : parentList) {
           COMMON_EXECUTOR.submit(() -> dealChildList(childList));
        }

    }

    public static void dealChildList(List<String> childList) {
        for (String child : childList) {
            COMMON_EXECUTOR.submit(() -> dealChild(child));
        }
    }

    public static void dealChild(String child) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("taskcount = {},ActiveCount = {}",COMMON_EXECUTOR.getTaskCount(),COMMON_EXECUTOR.getActiveCount());
        //System.out.println(Thread.currentThread().getName() + " value:" + child);
    }
}
