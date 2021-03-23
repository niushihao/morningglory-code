package com.morningglory.binlog.config;

import com.alibaba.otter.canal.adapter.launcher.loader.CanalAdapterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qianniu
 * @date 2020/11/24 4:23 下午
 * @desc
 */
//@Configuration
public class ConfigBean {

    @ConditionalOnMissingBean
    //@Bean
    public CanalAdapterService canalAdapterService(){
        return new CanalAdapterService();
    }
}
