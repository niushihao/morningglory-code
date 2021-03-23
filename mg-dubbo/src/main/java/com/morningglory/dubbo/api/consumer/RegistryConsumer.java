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
import org.apache.dubbo.config.utils.ReferenceConfigCache;

/**
 * @Author: qianniu
 * @Date: 2020-05-27 14:58
 * @Desc: 通过注册中心获取提供者并消费
 * 1.如果自己指定了超时时间则以自己配置的为主
 * 2.如果没有配置超时时间则用服务端的超时时间来
 */
@Slf4j
public class RegistryConsumer {

    public static void main(String[] args) throws InterruptedException {

        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);
        reference.setRetries(0);
        reference.setTimeout(1000);

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        registryConfig.setTimeout(10000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
                .registry(registryConfig)
                .protocol(new ProtocolConfig("dubbo"))
                .reference(reference)
                //.reference(r->r.build().setInterface(DemoService.class))
                .start();

        DubboResponse response = ReferenceConfigCache.getCache().get(reference).get(1);
        log.info("response = {}",response);

        String message = ReferenceConfigCache.getCache().get(reference).sayHi(new DubboRequest().setId(1));
        log.info("message = {}",message);

    }

}
