package com.morningglory.basic.guava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @Author: qianniu
 * @Date: 2019-01-25 15:47
 * @Desc:
 */
public class DataObServer1 {
    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     *
     * @param msg
     */
    @Subscribe
    public void fun(String msg){
        System.out.println("msg ="+msg);
    }
}
