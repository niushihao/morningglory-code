package com.morningglory.mvc.listener;

import com.morningglory.mvc.canal.CanalDispatcher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: qianniu
 * @Date: 2019-12-02 16:19
 * @Desc:
 */
@Component
public class ApplicationStopListener implements ApplicationListener<ContextStoppedEvent> {

    @Resource
    private CanalDispatcher canalDispatcher;

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        canalDispatcher.destroy();
    }
}
