package com.morningglory.basic.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qianniu
 * @date 2020/6/24 10:54 上午
 * @desc
 */
@Slf4j
public class TransactionTest {

    private final static String base_package = "com.morningglory.basic.spring.transaction";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);
        TransactionBean bean = context.getBean(TransactionBean.class);
        log.info(bean.getMsg());

    }


}
