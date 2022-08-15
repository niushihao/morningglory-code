package com.morningglory.basic.spring.listener;

import com.morningglory.basic.spring.listener.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
@Slf4j
@Component
public class AnnotationApplicationListener {

    @EventListener
    public void listener(MyEvent event){
        log.info("收到了 MyGenericApplicationListener 的消息");
    }
}
