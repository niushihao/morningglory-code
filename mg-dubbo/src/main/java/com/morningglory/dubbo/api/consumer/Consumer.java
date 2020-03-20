package com.morningglory.dubbo.api.consumer;

import com.alibaba.fastjson.JSON;
import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.module.DubboResponse;
import com.morningglory.dubbo.service.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;

/**
 * @author qianniu
 * @version 1.0
 * @desc injvm调用时 timeout参数无效，直连调用时没有配timeout默认是1s,不会使用服务端的超时，通过注册中心调用如果没有配timeout,就是用服务端的timeout
 * @date 2018-12-14 20:22
 * @see
 */
public class Consumer {

  public static void main(String[] args) throws InterruptedException {

    ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
    reference.setInterface(DemoService.class);
    reference.setUrl("127.0.0.1:4444");

    DubboBootstrap bootstrap = DubboBootstrap.getInstance();
    bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
            .registry(new RegistryConfig("N/A"))
            .protocol(new ProtocolConfig("dubbo"))
            .reference(reference)
            .start();

    DubboResponse response = ReferenceConfigCache.getCache().get(reference).get(1);
    System.out.println(JSON.toJSONString(reference));

    String message = ReferenceConfigCache.getCache().get(reference).sayHi(new DubboRequest().setId(1));
    System.out.println(message);

  }

}
