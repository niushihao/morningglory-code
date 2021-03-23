package com.morningglory.design.factory.common.scenes;

import com.morningglory.design.factory.common.factory.BlackHumanFactory;
import com.morningglory.design.factory.common.factory.HumanFactory;
import com.morningglory.design.factory.common.factory.YellowHumanFactory;
import com.morningglory.design.factory.common.product.Human;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc
 * 工厂模式, 和简单工厂模式的区别是每个产品都有一个工厂与之对应
 * 所有工厂方法中不需要传递参数
 */
@Slf4j
public class NvWa {

    public static void main(String[] args) {

        HumanFactory yellowHumanFactory = new YellowHumanFactory();
        Human yellowHuman = yellowHumanFactory.create();
        yellowHuman.getColor();

        HumanFactory blackHumanFactory = new BlackHumanFactory();
        Human blackHuman = blackHumanFactory.create();
        blackHuman.getColor();

    }
}
