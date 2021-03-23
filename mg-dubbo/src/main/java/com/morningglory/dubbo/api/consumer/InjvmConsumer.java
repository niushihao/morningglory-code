package com.morningglory.dubbo.api.consumer;

import com.morningglory.dubbo.api.provider.Provider;
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
import org.apache.dubbo.rpc.Constants;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author qianniu
 * @version 1.0
 * @date 2018-12-14 20:22
 * @desc injvm调用时 timeout参数无效，
 * 直连调用时没有配timeout默认是1s,不会使用服务端的超时，
 * 通过注册中心调用如果没有配timeout,就是用服务端的timeout
 *
 * 本质上injvm协议只能在同一个进程内调用,也就是提供者和消费者需要在同一个进程中
 */
@Slf4j
public class InjvmConsumer {

  public static void main(String[] args) throws InterruptedException {

    // injvm测试只能在同一个main方法中同时启动提供者和消费者
    Provider.createInJvmProvider().start();

    ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
    reference.setInterface(DemoService.class);
    reference.setScope(Constants.LOCAL_KEY);
    reference.setCheck(false);

    DubboBootstrap bootstrap = DubboBootstrap.getInstance();
    bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
            .reference(reference)
            .start();

    RpcContext.getContext().setAttachment("key","value");
    DubboResponse response = ReferenceConfigCache.getCache().get(reference).get(1);
    log.info("response = {}",response);

    String message = ReferenceConfigCache.getCache().get(reference).sayHi(new DubboRequest().setId(1));
    log.info("message = {}",message);

  }

}
