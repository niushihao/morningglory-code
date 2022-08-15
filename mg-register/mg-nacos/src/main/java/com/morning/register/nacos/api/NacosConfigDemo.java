package com.morning.register.nacos.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractSharedListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qianniu
 * @date 2022/8/10
 * @desc
 */
@Slf4j
public class NacosConfigDemo {

    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws NacosException {

        Properties properties = new Properties();
        // 指定 Nacos 地址
        properties.put(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");

        ConfigService configService = NacosFactory.createConfigService(properties);

        Runnable job = () -> {
            // 指定配置的 DataID 和 Group
            String dataId = "testDataId";
            String group = "DEFAULT_GROUP";
            String content = "connectTimeoutInMills=5000";

            String config = null;
            try {
                config = configService.getConfig(dataId, group, 5000);
                log.info("query config = {}",config);
                configService.addListener(dataId,group, new AbstractSharedListener() {

                    @Override
                    public void innerReceive(String dataId, String group, String configInfo) {
                        log.info("receiveConfigInfo,configInfo = {}",configInfo);
                    }
                });

                //configService.publishConfig(dataId,group,"test");
            } catch (NacosException e) {
                e.printStackTrace();
            }

        };

        executor.execute(job);
    }
}
