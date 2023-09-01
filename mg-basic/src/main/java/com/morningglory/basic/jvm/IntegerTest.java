package com.morningglory.basic.jvm;

/**
 * @author qianniu
 * @date 2023/1/6
 * @desc
 */
public class IntegerTest {

    public static void main(String[] args) {
        Integer i1 = 10;
        int i2 = 10;
        System.out.println(i1 == i2);

        Integer i3 = 5;
        Integer i4 = 5;
        System.out.println(i3 == i4);

        Integer i5 = 128;
        Integer i6 = 128;
        System.out.println(i5 == i6);
    }
}
