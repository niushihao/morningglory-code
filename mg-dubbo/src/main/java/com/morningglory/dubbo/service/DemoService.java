package com.morningglory.dubbo.service;


import com.morningglory.dubbo.module.DubboRequest;

/**
 * @author qianniu
 * @version 1.0
 * @desc
 * @date 2018-09-14 11:35
 * @see
 */
public interface DemoService<T> {

  String sayHi(T str) throws InterruptedException;

  String sayHi(DubboRequest request, String name, Integer age);

  String sayHi(DubboRequest request, Long age);

}
