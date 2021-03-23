package com.morningglory.design.factory.common.factory;

import com.morningglory.design.factory.common.product.BlackHuman;
import com.morningglory.design.factory.common.product.Human;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc 黑人由单独的黑人工厂创建
 */
public class BlackHumanFactory implements HumanFactory{

    @Override
    public Human create() {
        return new BlackHuman();
    }
}
