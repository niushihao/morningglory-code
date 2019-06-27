package com.morningglory.basic.guava.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * @Author: qianniu
 * @Date: 2019-01-25 15:41
 * @Desc:
 */
public class EventBusCenter {

    private static EventBus eventBus = new EventBus();

    private EventBusCenter(){}

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    public static void post(Object obj) {
        eventBus.post(obj);
    }
}
