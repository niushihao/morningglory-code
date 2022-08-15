package com.morningglory.basic.spring.listener.event;

import org.springframework.context.PayloadApplicationEvent;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
public class MyPayloadEvent extends PayloadApplicationEvent<String> {
    /**
     * Create a new PayloadApplicationEvent.
     *
     * @param source  the object on which the event initially occurred (never {@code null})
     * @param payload the payload object (never {@code null})
     */
    public MyPayloadEvent(Object source, String payload) {
        super(source, payload);
    }
}
