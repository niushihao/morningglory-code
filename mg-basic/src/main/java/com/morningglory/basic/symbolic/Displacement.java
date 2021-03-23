package com.morningglory.basic.symbolic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/6/10 10:18 下午
 * @desc 位移运算测试
 * >> 1 表示向右移动一位 等同于 除2
 *   0010 -> 0001
 * << 1 表示向左移动一位 等同于 乘2
 *   0010 -> 0100
 */
@Slf4j
public class Displacement {

    public static void main(String[] args) {

        log.info("2右移1位 = {}",2 >> 1);
        log.info("2左移1位 = {}",2 << 1);

        int n =16;
        int i = n - (n >> 2);

        log.info("i = {}",i);
    }
}
