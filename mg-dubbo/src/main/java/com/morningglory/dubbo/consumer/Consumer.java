package com.morningglory.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.service.DemoService;

/**
 * @author qianniu
 * @version 1.0
 * @desc injvm调用时 timeout参数无效，直连调用时没有配timeout默认是1s,不会使用服务端的超时，通过注册中心调用如果没有配timeout,就是用服务端的timeout
 * @date 2018-12-14 20:22
 * @see
 */
public class Consumer {

  public static void main(String[] args) throws InterruptedException {
    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setName("NC");


    ReferenceConfig<DemoService> config = new ReferenceConfig();
    config.setApplication(applicationConfig);
    config.setInterface(DemoService.class);
    config.setUrl("127.0.0.1:4444");
    config.setCheck(false);
    config.setAsync(false);

    DemoService demoService = config.get();
    RpcContext.getContext().set("name","nsh1");
    RpcContext.getServerContext().set("name","nsh2");
    RpcContext.getContext().setAttachment("name", "userValue3");

    System.out.println(demoService.sayHi(new DubboRequest().setId(1)));

  }

}
