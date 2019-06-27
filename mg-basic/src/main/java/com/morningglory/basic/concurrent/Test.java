package com.morningglory.basic.concurrent;

/**
 * @Author: nsh
 * @Date: 2018/3/21
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        MyList myList = new MyList();

        MyThreadA threadA = new MyThreadA(myList);
        MyThreadB threadB = new MyThreadB(myList);
        threadA.setName("A");
        threadA.start();

        threadB.setName("B");
        threadB.start();

        Thread.sleep(10000);

        System.out.println("大小 ="+myList.getSize());
    }
}