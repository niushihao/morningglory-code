package com.morningglory.design.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/9/28 1:22 下午
 * @desc
 */
@Slf4j
public class BWMModel extends CarModel{
    @Override
    protected void start() {
        log.info("发动宝马。。。");
    }

    @Override
    protected void stop() {
        log.info("停止宝马。。。");
    }

    @Override
    protected void alarm() {
        log.info("宝马鸣笛声。。。");
    }

    @Override
    protected void engineBoom() {
        log.info("宝马发动机声。。。");
    }

    // 车这么贵,要发出声音让路人注意
    protected boolean isAlarm(){
        return true;
    }
}
