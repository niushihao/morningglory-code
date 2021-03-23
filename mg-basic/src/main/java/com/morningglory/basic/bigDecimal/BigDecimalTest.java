package com.morningglory.basic.bigDecimal;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author qianniu
 * @date 2020/8/17 4:15 下午
 * @desc
 */
@Slf4j
public class BigDecimalTest {

    public static void main(String[] args) {

        long num = 1001;
        log.info((BigDecimal.valueOf(num).divide(new BigDecimal(100))).toString());
    }
}
