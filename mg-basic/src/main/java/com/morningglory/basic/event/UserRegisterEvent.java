package com.morningglory.basic.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: nsh
 * @Date: 2018/4/19
 * @Description: 用户注册事件
 */
public class UserRegisterEvent extends ApplicationEvent {
    public UserRegisterEvent(String name) {
        super(name);
    }
}