package com.morningglory.basic.jvm;

/**
 * @author qianniu
 * @date 2023/1/6
 * @desc
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = "helloworld";
        String s2 = "hello" + "world";
        System.out.println(s1 == s2);

        String s3 = new String("hello") + new String("world");
        System.out.println(s1 == s3);
        String s4 = new String("helloword");
        System.out.println(s1 == s4);

    }
}
