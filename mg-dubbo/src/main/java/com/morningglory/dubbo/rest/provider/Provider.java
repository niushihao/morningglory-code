package com.morningglory.dubbo.rest.provider;

import com.morningglory.dubbo.service.DemoService;
import com.morningglory.dubbo.service.impl.UserServiceImpl;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ProtocolBuilder;
import org.apache.dubbo.config.bootstrap.builders.RegistryBuilder;

/**
 * @Author: qianniu
 * @Date: 2020-03-19 13:47
 * @Desc:
 */
public class Provider {

    public static void main(String[] args) {

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap
                .protocol(ProtocolBuilder.newBuilder().name("rest").port(4444).server("tomcat").build())
                .application("demo-rest")
                .registry(RegistryBuilder.newBuilder().address("zookeeper://127.0.0.1:2181").build())
                .service(s ->{
                    s.interfaceClass(DemoService.class);
                    s.ref(new UserServiceImpl());
                })
                .start()
                .await();
    }
}
