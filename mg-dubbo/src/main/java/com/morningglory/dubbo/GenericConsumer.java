package com.morningglory.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:59
 * @see
 */
public class GenericConsumer {

  public static void main(String[] args) {


    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setName("NC");

    ReferenceConfig<GenericService> config = new ReferenceConfig();
    config.setApplication(applicationConfig);
    config.setInterface("service.DemoService");
    //config.setRegistry(registryConfig);
    config.setUrl("127.0.0.1:4444");
    config.setCheck(false);
    config.setGeneric(true);
    GenericService genericService = config.get();

    Map<String,Object> map = new HashMap<>();
    map.put("class","module.DubboRequest");
    map.put("id",1);
    System.out.println(genericService.$invoke("sayHi",new String[]{"java.lang.Object"},
                                              new Object[]{map}));

  }

}
