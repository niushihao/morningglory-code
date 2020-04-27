package com.morningglory.basic.classload;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-04-14 13:44
 * @Desc:
 * 可以通过javap 将class文件 反解析出当前类对应的code区（汇编指令）、本地变量表、异常表和代码行偏移量映射表、常量池等等信息
 * javap -v mg-basic/target/classes/com/morningglory/basic/classload/Demo.class
 */
@Slf4j
public class Demo {

    public final static int num2 = getFinalNumber();
    public static int num1 = getNumber();
    static {
        System.out.println("静态块执行");
    }

    private static int getFinalNumber() {
        System.out.println("静态常量获取值");
        return 2;
    }

    private static int getNumber() {
        System.out.println("静态获取值");
        return 1;
    }


    public Demo() {
        log.info("构造方法，加载器为: {}",Demo.class.getClassLoader());
    }

    public void sayHi(){
        log.info("Hi...123455");
    }
}
