package com.morningglory.basic.concurrent.wait;

/**
 * @Author: nsh
 * @Date: 2018/3/27
 * @Description:
 */
public class Subtract {

    private String lock;

    public Subtract(String lock) {
        this.lock = lock;
    }

    public void subtract(){
        try {
            synchronized (lock){
                while (ValueObject.list.size() == 0){
                    System.out.println("wait begin ThreadName ="+Thread.currentThread().getName());
                    lock.wait();
                    System.out.println("wait end ThreadName ="+Thread.currentThread().getName());
                }

                ValueObject.list.remove(0);
                System.out.println("list size ="+ValueObject.list.size());

            }
        }catch (InterruptedException e) {

        }
    }
}