package com.morningglory.basic.factory;

/**
 * @Author: nsh
 * @Date: 2018/4/18
 * @Description:
 */
public class FoodFactory implements Factory{
    @Override
    public Product getFactory() {
        return new FoodProduct();
    }
}