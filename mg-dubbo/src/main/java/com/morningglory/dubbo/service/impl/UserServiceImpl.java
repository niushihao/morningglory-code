package com.morningglory.dubbo.service.impl;

import com.morningglory.dubbo.module.DubboRequest;
import com.morningglory.dubbo.module.DubboResponse;
import com.morningglory.dubbo.service.DemoService;
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
public class UserServiceImpl implements DemoService<DubboRequest> {

  @Override
  public String sayHi(DubboRequest str) throws InterruptedException {
    System.out.println(str.getId());
    //Thread.sleep(10000L);
    RpcContext context = RpcContext.getContext();
    System.out.println(context.get("name"));
    return "Hi,"+str;
  }

  @Override
  public String sayHi(DubboRequest request, String name,Integer age) {
    return "(str)Hello , "+ name +" and age ="+age;
  }

  @Override
  public String sayHi(DubboRequest request, Long age) {
    return "(long)Hello , "+ age;
  }

  @Override
  @GET
  @Path("{id : \\d+}")
  @Produces({MediaType.APPLICATION_JSON})
  public DubboResponse get(int id) {
    DubboResponse response = new DubboResponse();
    response.setId(id);
    response.setName("test");
    return response;
  }


}
