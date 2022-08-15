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
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    private static final int TEMP =   RUNNING | 0;


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("COUNT_BITS ="+COUNT_BITS);
        System.out.println("CAPACITY ="+CAPACITY);
        System.out.println("RUNNING ="+RUNNING);
        System.out.println("SHUTDOWN ="+SHUTDOWN);
        System.out.println("STOP ="+STOP);
        System.out.println("TIDYING ="+TIDYING);
        System.out.println("TERMINATED ="+TERMINATED);
        System.out.println("TEMP ="+TEMP);
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,6,10L
                , TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4));
        // 提交任务
        for(int i= 0; i< 10; i++){
            int temp = i;
            executor.execute(() -> System.out.println("123"));
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