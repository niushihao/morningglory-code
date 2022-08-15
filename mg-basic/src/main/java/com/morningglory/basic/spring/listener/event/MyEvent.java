package com.morningglory.basic.spring.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
public class MyEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
