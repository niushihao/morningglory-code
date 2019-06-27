package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:
 */
public class MyThread implements Runnable {


    @Override
    public void run() {
        try {
            //饿汉模式(线程安全)
            System.out.println(Thread.currentThread().getName()+"饿汉:"+HungrySingleton.getInstance().hashCode());

            //懒汉模式(非线程安全)
            System.out.println(Thread.currentThread().getName()+"懒汉:"+LazySingleton.getInstance().hashCode());

            //DCL懒汉模式（线程安全）
            System.out.println(Thread.currentThread().getName()+"DCL"+DCLsInglenton.getDcLsInglenton().hashCode());

            //静态代码块(线程安全)
            System.out.println(Thread.currentThread().getName()+"静态："+StaticSingleton.getInstance().hashCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread[] ts = new Thread[10];
        for(int i = 0; i< 10; i++){
            ts[i] = new Thread(new MyThread());
        }

        for(int i = 0; i< 10; i++){
            ts[i].start();
        }
    }
}