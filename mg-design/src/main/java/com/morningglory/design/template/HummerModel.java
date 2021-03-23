package com.morningglory.design.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/9/28 1:20 下午
 * @desc
 */
@Slf4j
public class HummerModel extends CarModel{
    @Override
    protected void start() {
        log.info("发动悍马。。。");
    }

    @Override
    protected void stop() {
        log.info("停止悍马");
    }

    @Override
    protected void alarm() {
        log.info("悍马鸣笛声");
    }

    @Override
    protected void engineBoom() {
        log.info("悍马发送机声");
    }
}
