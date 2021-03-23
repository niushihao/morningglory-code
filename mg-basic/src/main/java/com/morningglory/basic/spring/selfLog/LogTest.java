package com.morningglory.basic.spring.selfLog;

import com.morningglory.basic.spring.transaction.TransactionBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qianniu
 * @date 2020/6/24 3:37 下午
 * @desc
 */
@Slf4j
public class LogTest {

    private final static String base_package = "com.morningglory.basic.spring.selfLog";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(base_package);
        LogBean bean = context.getBean(LogBean.class);
        log.info(bean.getMsg());

    }
}
