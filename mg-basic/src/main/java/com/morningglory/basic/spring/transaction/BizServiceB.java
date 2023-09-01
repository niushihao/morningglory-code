package com.morningglory.basic.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qianniu
 * @date 2022/11/29
 * @desc
 */
@Component
@Slf4j
public class BizServiceB {

    public void invoke(){
        log.info("serviceB invoked...");
    }

    @Transactional
    public void invokeWithTransactional(){
        log.info("serviceB invoked...");
    }

    @Transactional
    public void invokeWithThrowRunTimeException(){
        log.info("serviceB invokeWithThrowRunTimeException...");
        throw new RuntimeException("123");
    }

    @Transactional
    public void invokeWithThrowError(){
        log.info("serviceB invokeWithThrowError...");
        throw new Error("123");
    }

    @Transactional
    public void invokeWithThrowBizException(){
        log.info("serviceB invokeWithThrowError...");
        throw new Error("123");
    }

    @Transactional
    public void invokeWithOutTransaction(){
        log.info("serviceB invokeWithOutTransaction...");
        throw new Error("123");
    }
}
