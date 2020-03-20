package com.morningglory.rpc.tomcat;

/**
 * @Author: qianniu
 * @Date: 2020-03-11 10:00
 * @Desc:
 */
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHi(String msg) {
        return msg;
    }

    @Override
    public DemoBean getDemo(Integer code, String msg) {
        DemoBean bean = new DemoBean();
        bean.setCode(code);
        bean.setMsg(msg);
        return bean;
    }
}
