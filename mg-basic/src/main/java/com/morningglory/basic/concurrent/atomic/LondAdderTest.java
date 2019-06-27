package com.morningglory.basic.concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * @Author: qianniu
 * @Date: 2019-04-16 23:18
 * @Desc:
 */
public class LondAdderTest {

    public static void main(String[] args) {

        LongAdder adder = new LongAdder();
        System.out.println("init long value ="+adder.longValue());

        adder.increment();
        adder.add(100000000000000L);
        System.out.println("after increment long value ="+adder.longValue());
        System.out.println("after increment long value ="+adder.floatValue());

        int a  = (4 - 1) >>> 1;
        System.out.println(a);
    }
}
