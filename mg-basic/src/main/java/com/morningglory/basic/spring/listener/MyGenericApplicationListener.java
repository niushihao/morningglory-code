package com.morningglory.basic.spring.listener;

import com.morningglory.basic.spring.listener.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
@Slf4j
@Component
public class MyGenericApplicationListener implements GenericApplicationListener {
    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return MyEvent.class.equals(eventType.getType());
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        MyEvent myEvent = (MyEvent) event;

        log.info("收到了 MyGenericApplicationListener 的消息");
    }
}
