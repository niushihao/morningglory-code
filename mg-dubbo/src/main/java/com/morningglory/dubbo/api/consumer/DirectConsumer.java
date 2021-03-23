package com.morningglory.dubbo.api.consumer;

import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.module.DubboResponse;
import com.morningglory.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ApplicationBuilder;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.config.utils.ReferenceConfigCache;

/**
 * @author qianniu
 * @date 2020/12/4 9:53 上午
 * @desc 直连调用
 */
@Slf4j
public class DirectConsumer {

    public static void main(String[] args) throws InterruptedException {

        DubboBootstrap.getInstance()
                .application(ApplicationBuilder.newBuilder().name("dubbo-demo-api-consumer").build())
                .reference(ReferenceBuilder.newBuilder()
                        .interfaceClass(DemoService.class)
                        .url("127.0.0.1:4444")
                        //.timeout(10000)
                        .build())
                .start();

        DubboRequest request = new DubboRequest();
        request.setId(1);
        String response = ReferenceConfigCache.getCache().get(DemoService.class).sayHi(request);
        log.info("response = {}",response);
    }
}
