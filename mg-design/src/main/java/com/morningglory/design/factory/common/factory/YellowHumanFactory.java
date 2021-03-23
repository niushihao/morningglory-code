package com.morningglory.design.factory.common.factory;

import com.morningglory.design.factory.common.product.Human;
import com.morningglory.design.factory.common.product.YellowHuman;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc 黑人由单独的黑人工厂创建
 */
public class YellowHumanFactory implements HumanFactory{

    @Override
    public Human create() {
        return new YellowHuman();
    }
}
