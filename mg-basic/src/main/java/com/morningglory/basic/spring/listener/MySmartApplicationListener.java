package com.morningglory.basic.spring.listener;

import com.morningglory.basic.spring.listener.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
@Slf4j
@Component
public class MySmartApplicationListener implements SmartApplicationListener {


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return MyEvent.class.equals(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        MyEvent myEvent = (MyEvent) event;

        log.info("收到了 MySmartApplicationListener 的消息");
    }
}
