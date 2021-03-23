package com.morningglory.basic.antTask;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author qianniu
 * @date 2020/7/31 12:45 上午
 * @desc
 */
public class FutureSynchronizer {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int[] times = {200,300,500,1000};
    public static void main(String[] args) {

        // 解析参数
        String key = args[0];
        String value = args[1];
        Task task = new Task(key,value);
        // 开始处理
        new FutureSynchronizer().process(task);

    }

    public String process(Task task){

        Future<?> future = executorService.submit(() -> {
            task.execute();
        });

        int timeout = 3000;
        try {
            while (!future.isDone()){
                for(int time : times){
                    LockSupport.parkNanos(this,time * 200 * 1000 * 1000);
                    timeout-=time;
                }
                return String.valueOf(future.get(timeout,TimeUnit.MILLISECONDS));
            }
            return String.valueOf(future.get());
        }catch (Exception e){
            return null;
        }
    }
}
