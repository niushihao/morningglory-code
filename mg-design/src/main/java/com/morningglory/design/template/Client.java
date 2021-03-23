package com.morningglory.design.template;

/**
 * @author qianniu
 * @date 2020/9/28 1:24 下午
 * @desc
 */
public class Client {

    public static void main(String[] args) {

        CarModel hummer = new HummerModel();
        hummer.run();

        CarModel bwm = new BWMModel();
        bwm.run();
    }
}
