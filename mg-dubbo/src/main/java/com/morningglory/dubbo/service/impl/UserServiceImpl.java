package com.morningglory.dubbo.service.impl;

import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.service.DemoService;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:35
 * @see
 */
public class UserServiceImpl implements DemoService<DubboRequest> {

  @Override
  public String sayHi(DubboRequest str) throws InterruptedException {
    System.out.println(str.getId());
    Thread.sleep(10000L);
    return "Hi,"+str;
  }

  @Override
  public String sayHi(DubboRequest request, String name) {
    return "Hello , "+ name;
  }



}
