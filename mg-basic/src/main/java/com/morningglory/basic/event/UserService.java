package com.morningglory.basic.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @Author: nsh
 * @Date: 2018/4/19
 * @Description: 注册事件发布者
 */
public class UserService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher= applicationEventPublisher;
    }

    public void regiser(String name){

        System.out.println("用户"+name+"注册了。");
        applicationEventPublisher.publishEvent(new UserRegisterEvent(name));

    }
}