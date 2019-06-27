package com.morningglory.basic.thread;

/**
 * @Author: nsh
 * @Date: 2018/8/30
 * @Description: Thread对象中有个ThreadLocalMap属性，使用ThreadLocal时就是把值放入Thread对象的ThreadLocalMap中，从而达到每个线程有维护自己的数据副本，
 * ThreadLocalMap 的key是 ThreadLocal 对象，value是我们设置的值
 * ThreadLocalMap就是一个数组结构，现根据key的hashCode和数组长度算出索引位置，然后判断当前位置是否有值，如果有 使用 == 判断key是否相等，如果相等就进行value替换，如果不相等就向数组的下一个索引位置放值。
 */
public class ThreadLocalTest {

    private static int a = 234;
    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        new Thread(() ->{
            Thread.currentThread().setName("t1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a =456;
            threadLocal.set(a);
            System.out.println("t1 a ="+threadLocal.get());
        }).start();

        new Thread(() ->{
            Thread.currentThread().setName("t2");

            threadLocal.set(a);
            System.out.println("t2 a ="+threadLocal.get());
        }).start();

        System.out.println("a ="+a);

    }
}