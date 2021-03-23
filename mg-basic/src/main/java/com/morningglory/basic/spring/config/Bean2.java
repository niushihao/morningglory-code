package com.morningglory.basic.spring.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/7/9 10:20 下午
 * @desc
 */
@Slf4j
public class Bean2 {

    public Bean2(Bean1 bean1) {
        log.info("bean2 init");
    }
}
