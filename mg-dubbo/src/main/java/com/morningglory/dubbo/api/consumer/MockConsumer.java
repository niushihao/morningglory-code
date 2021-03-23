package com.morningglory.dubbo.api.consumer;

import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.module.DubboResponse;
import com.morningglory.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.config.bootstrap.builders.RegistryBuilder;
import org.apache.dubbo.config.utils.ReferenceConfigCache;

/**
 * @Author: qianniu
 * @Date: 2020-05-27 15:07
 * @Desc:
 */
@Slf4j
public class MockConsumer {

    public static void main(String[] args) throws InterruptedException {

        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);
        // 调用失败后返回一个默认值
        reference.setMock("fail:return empty");
        reference.setCheck(false);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
                .registry(RegistryBuilder.newBuilder().address("zookeeper://127.0.0.1:2181").timeout(10000).build())
                .reference(reference)
                //.reference(r->r.build().setInterface(DemoService.class))
                .start();

        DubboResponse response = ReferenceConfigCache.getCache().get(reference).get(1);
        log.info("response = {}",response);

        String message = ReferenceConfigCache.getCache().get(reference).sayHi(new DubboRequest().setId(1));
        log.info("message = {}",message);

    }
}
