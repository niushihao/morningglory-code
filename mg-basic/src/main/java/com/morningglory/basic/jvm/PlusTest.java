package com.morningglory.basic.jvm;

/**
 * @author qianniu
 * @date 2023/1/6
 * @desc
 */
public class PlusTest {

    public static void main(String[] args) {

        PlusTest plusTest = new PlusTest();

        plusTest.method1();

        plusTest.method2();

        plusTest.method3();

        plusTest.method4();
    }


    /**
     * 测试 i++ 和 ++i
     */
    public void method1() {
        int i = 10;
        ++i;
        System.out.println(i);
    }

    public void method2() {
        int i = 10;
        i++;
        System.out.println(i);
    }

    public void method3() {
        int i = 10;
        int j = i++;

        int m = 10;
        int n = ++m;

        System.out.println("j=" + j);
        System.out.println("n=" + n);
    }

    public void method4() {
        int i = 10;
        i = ++i;
        System.out.println(i);
    }
}
