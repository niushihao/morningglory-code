package com.morningglory.basic.concurrent.wait;

/**
 * @Author: nsh
 * @Date: 2018/3/27
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");

        Add add = new Add(lock);
        Subtract subtract = new Subtract(lock);

        ThreadSubtract threadSubtract1 = new ThreadSubtract(subtract);
        threadSubtract1.setName("sub1");
        threadSubtract1.start();

        ThreadSubtract threadSubtract2 = new ThreadSubtract(subtract);
        threadSubtract2.setName("sub2");
        threadSubtract2.start();

        Thread.sleep(1000);

        ThreadAdd threadAdd = new ThreadAdd(add);
        threadAdd.setName("add");
        threadAdd.start();

    }
}