package com.morningglory.basic.event;

import org.springframework.context.ApplicationListener;

/**
 * @Author: nsh
 * @Date: 2018/4/19
 * @Description: 事件订阅者
 */
public class EmailService implements ApplicationListener<UserRegisterEvent> {


    @Override
    public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
        System.out.println("邮件服务接到通知，给"+userRegisterEvent.getSource()+" 发邮件。。。。。");
    }
}