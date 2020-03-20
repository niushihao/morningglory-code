package com.morningglory.dubbo.api.provider;

import com.morningglory.dubbo.service.DemoService;
import com.morningglory.dubbo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import java.io.IOException;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:25
 * @see
 */
@Slf4j
public class Provider {

  public static void main(String[] args) throws InterruptedException, IOException {

    ServiceConfig<UserServiceImpl> service = new ServiceConfig<>();
    service.setInterface(DemoService.class);
    service.setRef(new UserServiceImpl());

    DubboBootstrap bootstrap = DubboBootstrap.getInstance();
    bootstrap.application(new ApplicationConfig("dubbo-demo-api-provider"))
            // export to local
            //.registry(new RegistryConfig("N/A"))
            // export to zk
            .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
            .protocol(new ProtocolConfig("dubbo",4444))
            .service(service)
            .start()
            .await();


  }
}