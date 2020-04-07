package com.morningglory.basic.asm.proxy;

/**
 * @Author: qianniu
 * @Date: 2020-03-31 13:23
 * @Desc: 希望当执行show方法时能打印方法耗时;使用ASM技术修改Class文件,在原来方法上加上打印耗时的方法
 */
public class Hello {

    public static void main(String[] args) {
        show();
    }

    public static void show(){
        System.out.println("Hello World");
    }

}
