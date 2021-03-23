package com.morningglory.dubbo.api.consumer;

import com.google.common.cache.RemovalListener;
import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.AbstractMethodBuilder;
import org.apache.dubbo.config.bootstrap.builders.ApplicationBuilder;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author qianniu
 * @date 2021/2/23 4:13 下午
 * @desc 异步直连调用
 */
@Slf4j
public class AsyncDirectConsumer {

    public static void main(String[] args) {
        DubboBootstrap.getInstance()
                .application(ApplicationBuilder.newBuilder().name("dubbo-demo-api-consumer").build())
                .reference((ReferenceConfig<?>) ReferenceBuilder.newBuilder()
                        .interfaceClass(DemoService .class)
                        .url("127.0.0.1:4444")
                        .async(true)
                        .timeout(20000)
                        .build())
                .start();


        DubboRequest request = new DubboRequest();
        request.setId(1);
//        String response = ReferenceConfigCache.getCache().get(DemoService.class).sayHi(request);
//        log.info("response = {}",response);
//
//        CompletableFuture<Object> completableFuture = RpcContext.getContext().getCompletableFuture();
//        completableFuture.whenComplete((returnValue,exception) -> {
//            if(Objects.isNull(exception)){
//                log.info("completableFuture returnValue = {},exception ={}",returnValue,exception);
//            }else {
//                log.info("completableFuture returnValue = {},exception ={}",returnValue,exception);
//            }
//        });

        CompletableFuture<String> future = RpcContext.getContext().asyncCall(() -> {
            String s = ReferenceConfigCache.getCache().get(DemoService.class).sayHi(request);

            RpcContext.getContext().getCompletableFuture();
            log.info("asyncCall returnValue = {}", s);
            return s;
        });
        //future.get();

    }

}
