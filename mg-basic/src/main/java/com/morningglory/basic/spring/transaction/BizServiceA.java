package com.morningglory.basic.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author qianniu
 * @date 2022/11/29
 * @desc
 */
@Component
@Slf4j
public class BizServiceA {

    @Resource
    private BizServiceB bizServiceB;

    @Transactional
    public void invokeAllHasTransactional(){
        log.info("serviceA invoked...");
        bizServiceB.invokeWithTransactional();
    }

    @Transactional
    public void invokeAHasTransactional(){
        log.info("serviceA invoked...");
        bizServiceB.invokeWithOutTransaction();
    }
}
