package com.morningglory.basic.thread;

/**
 * @Author: nsh
 * @Date: 2018/8/17
 * @Description: 交替打印奇数偶数
 */
public class Print {

    private static Object lock = new Object();

    private static boolean flag;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            Thread.currentThread().setName("打印偶数的线程");
            String name = Thread.currentThread().getName();
            synchronized (lock){
                for(int i =1;i<101;i++) {
                    if (flag) {
                        System.out.println("name =" + name + ",num =" + i);
                        flag = false;
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }).start();

        Thread.sleep(1000);

        new Thread(()->{
            Thread.currentThread().setName("打印奇数的线程");
            String name = Thread.currentThread().getName();
            synchronized (lock) {
                for(int i =1;i<101;i++){

                    if (!flag) {
                        System.out.println("name ="+name+",num =" + i);
                        flag = true;
                        lock.notify();
                    }else{
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }
}