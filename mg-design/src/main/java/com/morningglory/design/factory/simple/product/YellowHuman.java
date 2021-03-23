package com.morningglory.design.factory.simple.product;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc
 */
@Slf4j
public class YellowHuman implements Human{
    @Override
    public void getColor() {
        log.info("color is yellow");
    }

    @Override
    public void talk() {
        log.info("i am yellow man");
    }
}
