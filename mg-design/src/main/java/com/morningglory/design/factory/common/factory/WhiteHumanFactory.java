package com.morningglory.design.factory.common.factory;

import com.morningglory.design.factory.common.product.Human;
import com.morningglory.design.factory.common.product.WhiteHuman;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc 白人由单独的白人工厂创建
 */
public class WhiteHumanFactory implements HumanFactory{

    @Override
    public Human create() {
        return new WhiteHuman();
    }
}
