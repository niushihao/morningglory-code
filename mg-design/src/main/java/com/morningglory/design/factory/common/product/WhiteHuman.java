package com.morningglory.design.factory.common.product;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc
 */
@Slf4j
public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        log.info("color is white");
    }

    @Override
    public void talk() {
        log.info("i am white man");
    }
}
