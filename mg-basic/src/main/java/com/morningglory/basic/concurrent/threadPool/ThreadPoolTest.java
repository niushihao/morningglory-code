package com.morningglory.basic.concurrent.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: nsh
 * @Date: 2018/7/5
 * @Description:
 */
@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,6,10L
                , TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4));
        // 提交任务
        for(int i= 0; i< 10; i++){
            int temp = i;
            Future<Integer> future = executor.submit(() -> {
                log.info("invoke");
                Thread.sleep(3000);
                if(temp == 9){
                    Thread.currentThread().interrupt();
                }
                return temp +2;
            });

            //获取结果
            //Integer integer = future.get();
        }
        Thread.sleep(5000);
        executor.shutdownNow();

//        List<Runnable> list = new ArrayList<>();
//        for(int i =1;i<11;i++){
//            Runnable t = () -> {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//               System.out.println("threadName:"+Thread.currentThread().getName());
//            };
//            list.add(t);
//        }
//
//        //Executors.callable
//        list.stream().forEach(t -> {
//            Future<?> submit = executor.submit(t);
//            try {
//                submit.get();
//                submit.get();
//                submit.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//        });



    }
}