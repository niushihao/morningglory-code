package com.morningglory.basic.spring.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author qianniu
 * @date 2020/6/24 11:22 上午
 * @desc
 */
@Component
public class AsyncBean {

    /**
     *
     */
    @Autowired
    AsyncBean asyncBean;

    @Async
    public String getMsg(){
        return "async";
    }

    public Future<String> getFutureMsg(){
        return new AsyncResult<>("123");
    }
}
