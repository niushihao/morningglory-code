package com.morningglory.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.morningglory.dubbo.module.DubboRequest;

import java.lang.reflect.Method;
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

  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {


    ApplicationConfig applicationConfig = new ApplicationConfig();
    applicationConfig.setName("NC");

    // 测试直连的泛化调用
    invokeWithOutRegistry(applicationConfig);
  }

  /**
   * 直连的方式调用
   * 此接口需要 自定义对象和String两个入参
   * @param applicationConfig
   */
  private static void invokeWithOutRegistry(ApplicationConfig applicationConfig) {
    ReferenceConfig<GenericService> config = new ReferenceConfig();
    config.setApplication(applicationConfig);
    config.setInterface("com.morningglory.dubbo.service.DemoService");
    config.setUrl("127.0.0.1:4444");
    config.setCheck(true);
    config.setGeneric(true);
    GenericService genericService = config.get();

    Map<String,Object> map = new HashMap<>();
    map.put("class","com.morningglory.dubbo.module.DubboRequest");
    map.put("id",1);
    System.out.println(genericService.$invoke("sayHi",new String[]{"com.morningglory.dubbo.module.DubboRequest","java.lang.Long"},new Object[]{map,null}));
  }

}
