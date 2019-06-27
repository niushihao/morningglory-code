package com.morningglory.basic.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: nsh
 * @Date: 2018/7/1
 * @Description:
 */
public class ThreadTest {

    private static int a =1;
    public static void main(String[] args) throws InterruptedException {
        a =2;
        System.out.println("begin a ="+a);
        Runnable r = () ->{
            try {
                Thread.currentThread().setName("123");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.interrupted();

            a=3;
            System.out.println(" run a ="+a);
        };
        Thread t = new Thread(r);

        t.start();

        //t.isInterrupted();
        //t.join(100);

        //t.interrupt();

        //让主线程等待 t线程执行结束
        t.join();
        System.out.println("man end...& a="+a);
    }
}