package com.morningglory.basic.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: nsh
 * @Date: 2018/8/17
 * @Description:阻塞队列
 */
public class MyQueue<E> {

    private Object[] items;

    private  ReentrantLock lock;

    private  Condition notEmpty;

    private  Condition notFull;

    private int count;


    public MyQueue(int capacity){

        items = new Object[capacity];
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }


    public boolean add(E e){

        lock.lock();
        if(items.length == count){
            return false;
        }
        items[count] = e;
        count++;
        lock.unlock();
        return true;
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        while(items.length == count){
            System.out.println(Thread.currentThread().getName()+":队里已满，需要等有位置才能放。");
            notFull.await();
        }
        System.out.println(Thread.currentThread().getName()+":队里终于有位置了，我要开始放了。");
        items[count] = e;
        count++;
        notEmpty.signal();
        lock.unlock();
    }

    public E take() throws InterruptedException {

        lock.lock();
        while (count == 0){
            System.out.println(Thread.currentThread().getName()+":队里为空，需要等到有数据才能拿。");
            notEmpty.await();
        }
        System.out.println(Thread.currentThread().getName()+":终于等到数据了，我要开始取了。");

        E e = (E) items[0];
        System.arraycopy(items,1,items,0,count-1);
        count--;
        notFull.signal();
        lock.unlock();
        return e;
    }

    public static void main(String[] args) throws InterruptedException {

        MyQueue<String> queue = new MyQueue<>(5);

        new Thread(() ->{
            Thread.currentThread().setName("放数据的线程");
            try {

                while (true) {
                    Thread.sleep(500);
                    queue.put("haha");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() ->{
            Thread.currentThread().setName("取数据的线程");
            try {
                while (true){
                    Thread.sleep(800);
                    queue.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}