package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        Thread[] ts = new Thread[10];
        for(int i = 0; i< 10; i++){
            ts[i] = new Thread(new MyThread());
        }

        for(int i = 0; i< 10; i++){
            ts[i].start();
        }
      int a = "ding4ccc9f9f5f3380ca35c2f4657eb6378f".hashCode()%10;
        System.out.println(a);
    }
}