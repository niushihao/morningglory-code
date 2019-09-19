package com.morningglory.dubbo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.morningglory.dubbo.service.DemoService;
import com.morningglory.dubbo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

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
    DemoService userService = new UserServiceImpl();

    RpcContext.getContext().set("name", "provider_qn");
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
    userConfig.setInterface("com.morningglory.dubbo.service.DemoService");
    userConfig.setRef(userService);
    userConfig.setApplication(applicationConfig);
    userConfig.setRegistry(registryConfig);
    //userConfig.setProtocol(protocol);
    userConfig.setTimeout(20000);
    userConfig.setAsync(false);

    userConfig.export();

    System.in.read();

    Runtime.getRuntime().addShutdownHook(new Thread() {

              public void run() {
                try {
                  log.info("## stop the canal client");
                } catch (Throwable e) {
                } finally {
                }
              }


            });
  }
}