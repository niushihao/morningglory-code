package com.morningglory.basic.concurrent.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qianniu
 * @date 2024/4/1
 * @desc
 */
public class ThreadLocalTest {

    private static final ThreadLocal<String> T_S = ThreadLocal.withInitial(() -> "1");
    private static final ThreadLocal<Integer> T_I = ThreadLocal.withInitial(() -> 1);

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
    private static AtomicInteger nextHashCode =
            new AtomicInteger();


    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        T_S.set("2");
        T_I.set(2);

        String s = T_S.get();
        Integer i = T_I.get();
        System.out.println(s+i);

        for (int j=0;j<100;j++) {
            int hj = nextHashCode() & 15;
            System.out.println(hj);
        }
    }
}
