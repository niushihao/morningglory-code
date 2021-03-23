package com.morningglory.dubbo.service.impl;

import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.module.DubboResponse;
import com.morningglory.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:35
 * @see
 */
@Path("/user")
@Slf4j
public class UserServiceImpl implements DemoService<DubboRequest> {

  @Override
  public String sayHi(DubboRequest str) {
    System.out.println(str.getId());
    RpcContext context = RpcContext.getContext();
    System.out.println(context.get("name"));
    return "Hi,"+str;
  }

  @Override
  public String sayHiWithSleep(DubboRequest request) {
    System.out.println(request.getId());
    try {
      Thread.sleep(10000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    RpcContext context = RpcContext.getContext();
    System.out.println(context.get("name"));
    return "Hi,"+request;
  }

  @Override
  public String sayHi(DubboRequest request, String name,Integer age) {
    return "(str)Hello , "+ name +" and age ="+age;
  }

  @Override
  public String sayHi(DubboRequest request, Long age) {
    return "(Long)Hello , "+ age;
  }

  @Override
  public String sayHi(DubboRequest request, long age) {
    return "(long)Hello , "+ age;
  }

  @Override
  @GET
  @Path("{id : \\d+}")
  @Produces({MediaType.APPLICATION_JSON})
  public DubboResponse get(int id) {
    log.info("get(int id) invoke ...");
    DubboResponse response = new DubboResponse();
    response.setId(id);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    response.setName("int");
    return response;
  }

  @Override
  public DubboResponse get(Integer id) {
    log.info("get(int id) invoke ...");
    DubboResponse response = new DubboResponse();
    response.setId(id);
    response.setName("Integer");
    return response;
  }


}
