package com.morningglory.basic.factory;

/**
 * @Author: nsh
 * @Date: 2018/4/18
 * @Description:
 */
public class FoodProduct implements Product{
    @Override
    public void created() {
        System.out.println("food has bean created!!");
    }
}