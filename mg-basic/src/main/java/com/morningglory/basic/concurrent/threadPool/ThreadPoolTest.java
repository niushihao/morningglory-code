package com.morningglory.basic.concurrent.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: nsh
 * @Date: 2018/7/5
 * @Description:
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,0L
                , TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100));
        // 提交任务
        Future<Integer> future = executor.submit(() -> {
            return 1+2;
        });
        //获取结果
        Integer integer = future.get();

        List<Runnable> list = new ArrayList<>();
        for(int i =1;i<11;i++){
            Runnable t = () -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               System.out.println("threadName:"+Thread.currentThread().getName());
            };
            list.add(t);
        }

        //Executors.callable
        list.stream().forEach(t -> {
            Future<?> submit = executor.submit(t);
            try {
                submit.get();
                submit.get();
                submit.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });



    }
}