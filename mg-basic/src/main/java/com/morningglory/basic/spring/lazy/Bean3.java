package com.morningglory.basic.spring.lazy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2020/6/15 1:50 下午
 * @desc
 */
@Component
@Lazy
@Slf4j
public class Bean3{
    public Bean3() {
        log.info("Bean3 init");
    }

    private Bean2 bean2;
}