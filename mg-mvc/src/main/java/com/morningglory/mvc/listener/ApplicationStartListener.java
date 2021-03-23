package com.morningglory.mvc.listener;

import com.morningglory.mvc.canal.CanalClient;
import com.morningglory.mvc.canal.CanalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-12-01 13:09
 * @Desc:
 */
@Slf4j
@Component
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent>{

    @Resource
    private CanalClient canalClient;

    @Resource
    private CanalListener canalListener;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            log.info("容器启动成功,开始连接canal");
            canalClient.setListener(canalListener);
            canalClient.init();
        }catch (Exception e){
            log.error("canal连接失败。。");
        }

    }

}
