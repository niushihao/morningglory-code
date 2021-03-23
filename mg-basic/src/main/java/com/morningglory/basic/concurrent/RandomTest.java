package com.morningglory.basic.concurrent;


/**
 * @author qianniu
 * @date 2020/6/11 1:55 下午
 * @desc
 */
public class RandomTest {

    public static void main(String[] args) {

        int test = test();
        System.out.println(test);
    }

    public static int test() {

        int a = 1;
        int b = 2;
        try {
            a =2;
            return a;

        }finally {
            a = 3;
            b = 4;
            return a;
        }
    }
}
