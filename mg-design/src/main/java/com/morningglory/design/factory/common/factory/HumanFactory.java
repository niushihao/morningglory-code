package com.morningglory.design.factory.common.factory;

import com.morningglory.design.factory.common.product.Human;

/**
 * @author qianniu
 * @date 2020/9/24 9:45 上午
 * @desc 工厂模式,和简单工厂模式的区别是每个产品都有一个工厂与之对应
 * 所有工厂方法中不需要传递参数
 */
public interface HumanFactory {

    Human create();
}
