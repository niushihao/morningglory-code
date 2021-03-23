package com.morningglory.basic.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qianniu
 * @date 2020/6/24 11:23 上午
 * @desc
 * spring解决循环依赖时,注入的bean不能被代理,否则会报错,AsyncBean模拟了循环依赖且被代理的情况
 * 相同的问题可以参考LogTest
 * @see AsyncBean
 * @see com.morningglory.basic.spring.selfLog.LogBean
 */
@Slf4j
public class AsyncTest {

    private final static String base_package = "com.morningglory.basic.spring.async";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);
        AsyncBean bean = context.getBean(AsyncBean.class);
        log.info(bean.getMsg());

    }
}
