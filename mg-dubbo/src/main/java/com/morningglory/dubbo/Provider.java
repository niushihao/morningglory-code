package com.morningglory.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.morningglory.dubbo.service.DemoService;
import com.morningglory.dubbo.service.impl.UserServiceImpl;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:25
 * @see
 */
public class Provider {

  public static void main(String[] args) throws InterruptedException {
    DemoService userService = new UserServiceImpl();

    // application
    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setName("nsh");

    // registry
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("N/A");

    // protocol
    ProtocolConfig protocol = new ProtocolConfig();
    protocol.setPort(4444);

    // service
    ServiceConfig<DemoService> userConfig = new ServiceConfig();
    userConfig.setInterface("service.DemoService");
    userConfig.setRef(userService);
    userConfig.setApplication(applicationConfig);
    userConfig.setRegistry(registryConfig);
    userConfig.setProtocol(protocol);
    userConfig.setTimeout(20000);
    userConfig.setAsync(false);

    userConfig.export();

  }



}
