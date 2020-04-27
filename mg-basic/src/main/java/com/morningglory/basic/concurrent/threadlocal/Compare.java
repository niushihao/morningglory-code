package com.morningglory.basic.concurrent.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: qianniu
 * @Date: 2020-01-20 09:52
 * @Desc:
 * 1.threadLocal不能再父子线程传递，integerInheritableThreadLocal可以
 * 2.使用线程池时 integerInheritableThreadLocal也不能传递
 */
@Slf4j
public class Compare {

    static ThreadLocal<Integer> threadLocal;
    static InheritableThreadLocal<Integer> integerInheritableThreadLocal ;



    public static void main(String[] args) {
        init();

        // 手动创建线程时,integerInheritableThreadLocal可以自动复制父线程的值
        testNewThread();

        // 正常来说线城池中的worker线程是不能获取到值的，但是这个例子能获取到，，，，
        testThreadPool();
    }


    /**
     * threadLocal值为空
     * threadName = Thread-0,threadLocal =null,integerInheritableThreadLocal = 1
     */
    public static void testNewThread(){
        Thread thread = new Thread(() -> {
            log.info("testNewThread:threadName = {},threadLocal ={},integerInheritableThreadLocal = {}"
                    ,Thread.currentThread().getName()
                    ,threadLocal.get()
                    ,integerInheritableThreadLocal.get()
            );
        });
        thread.start();
    }


    public static void testThreadPool(){
        Runnable runnable = () -> {
            log.info("testThreadPool:threadName = {},threadLocal ={},integerInheritableThreadLocal = {}"
                    ,Thread.currentThread().getName()
                    ,threadLocal.get()
                    ,integerInheritableThreadLocal.get()
            );
        };
        ExcutorHolder.executor.execute(runnable);
        Callable callable = () ->{
            log.info("testThreadPool:threadName = {},threadLocal ={},integerInheritableThreadLocal = {}"
                    ,Thread.currentThread().getName()
                    ,threadLocal.get()
                    ,integerInheritableThreadLocal.get()
            );
            return 1;
        };
        ExcutorHolder.executor.submit(callable);
    }

    public static void init(){
        threadLocal = new ThreadLocal<>();
        integerInheritableThreadLocal = new InheritableThreadLocal<>();
        log.info("Compare init start");
        threadLocal.set(1);
        integerInheritableThreadLocal.set(1);
        log.info("Compare init end");
    }

    public static class ExcutorHolder{
        static ExecutorService executor;
        static {
            log.info("ExcutorHolder init start");
            executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                log.info("name = {}",Thread.currentThread().getName());
            });
            log.info("ExcutorHolder init end");
        }
    }
}
