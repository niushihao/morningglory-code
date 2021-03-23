package com.morningglory.design.factory.simple.scenes;

import com.morningglory.design.factory.simple.factory.HumanFactory;
import com.morningglory.design.factory.simple.product.Human;
import com.morningglory.design.factory.simple.product.WhiteHuman;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc 简单工厂场景类
 * 简单工厂模式,又称静态工厂模式
 * 当然可以不使用静态方法
 */
@Slf4j
public class NvWa {

    public static void main(String[] args) {

        Human black = HumanFactory.createHuman("black");
        black.getColor();

        WhiteHuman human = HumanFactory.createHuman(WhiteHuman.class);
        human.getColor();

    }


}
