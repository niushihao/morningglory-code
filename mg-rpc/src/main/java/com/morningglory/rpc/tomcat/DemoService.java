package com.morningglory.rpc.tomcat;

/**
 * @Author: qianniu
 * @Date: 2020-03-11 09:59
 * @Desc:
 */
public interface DemoService {

    String sayHi(String msg);

    DemoBean getDemo(Integer code,String msg);
}
