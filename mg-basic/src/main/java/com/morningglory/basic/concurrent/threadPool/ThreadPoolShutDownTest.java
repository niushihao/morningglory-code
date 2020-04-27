package com.morningglory.basic.concurrent.threadPool;

import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2020-04-23 11:01
 * @Desc: 测试线程池关闭
 * 1.无论是shutdownNow还是shutdown 都只是设置了worker线程的中断状态
 * 2.如果线程处于阻塞状态则会被中断唤醒,如果处于运行状态则不会受到影响,
 *   如果想停止运行中线程需要线程运行时(run方法内)不停的判断自己的状态
 * 3.shutdown 只会设置空闲worker为中断状态(通过worker.tryLock判断,因为worker运行时会加锁),且不会处理队列中的任务
 * 4.shutdownNow 是把所有worker设置为中断状态,且会取出队列中的所有任务并返回
 */
@Slf4j
public class ThreadPoolShutDownTest {

    private static NamedThreadFactory factory = new NamedThreadFactory("shutdown",false,0);

    public static void main(String[] args) {

        //testShutDown();
        
        
        testShutDownNow();



    }

    private static void testShutDownNow() {
        // 先在队列中放入任务
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue(100);
        for(int i = 0; i < 10; i++){
            int temp = i;
            Runnable runnable = () -> {
                log.info("task_"+temp +" run ...");
            };
            queue.add(runnable);
        }
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,0L
                , TimeUnit.SECONDS,queue,factory);

        for(int i=0;i<3;i++){
            int temp = i;
            Runnable runnable = () -> {

                // 线程自己中断自己,并不会影响执行
                log.info("worker_{} run ...", temp);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);
        }

        List<Runnable> runnables = executor.shutdownNow();
        log.info("queue size after shutdown = {}",queue.size());
    }

    /**
     * 1.设置线程池的状态为 shutdown
     * 2.遍历所有worker,设置[空闲worker](通过worker.tryLock的是空闲的)的线程中断状态
     */
    private static void testShutDown() {
        // 先在队列中放入任务
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue(100);
        for(int i = 0; i < 10; i++){
            int temp = i;
            Runnable runnable = () -> {
                log.info("task_"+temp +" run ...");
            };
            queue.add(runnable);
        }

        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,0L
                , TimeUnit.SECONDS,queue);

        Runnable runnable = () -> {

            // 线程自己中断自己,并不会影响执行
            Thread.currentThread().interrupt();
            log.info("task run ...");
        };
        executor.execute(runnable);
        executor.shutdown();
        log.info("queue size after shutdown = {}",queue.size());

        executor.execute(runnable);
        log.info("*****************testShutDown end*******************");
    }
}
