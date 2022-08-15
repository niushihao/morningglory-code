package com.morningglory.basic.spring.listener;

import com.morningglory.basic.spring.listener.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
@Slf4j
@Component
public class MyApplicationListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("收到了 MyGenericApplicationListener 的消息");
    }
}
