package com.morningglory.basic.spring.listener;

import com.morningglory.basic.spring.listener.event.MyEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qianniu
 * @date 2022/8/11
 * @desc
 */
public class ListenerTest {

    public static void main(String[] args) {

        String basePackage = "com.morningglory.basic.spring.listener";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackage);
        context.publishEvent(new MyEvent("123"));
    }
}
