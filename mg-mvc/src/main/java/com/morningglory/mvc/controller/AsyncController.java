package com.morningglory.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qianniu
 * @date 2022/8/3
 * @desc 异步rest请求
 * 1、使用springboot封装后的方式,只需要将返回值包装成Callable就行
 * 2、使用servlet原生方式,需要手动开启异步(request.startAsync),然后将请求上下文放入异步业务线程中,组装好返回数据后 完成调用(asyncContext.complete())
 *
 */
@RestController
@RequestMapping("async/")
@Slf4j
public class AsyncController {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @RequestMapping("/v1")
    public Callable<String> v1(){

        log.info("AsyncController#v1 called. Thread: {}",Thread.currentThread().getName());
        Callable<String> callable = new Callable<String>() {

            public String call() throws Exception {
                log.info("controller-callable#async task started. Thread: " +
                        Thread.currentThread()
                                .getName());
                Thread.sleep(300);
                log.info("controller-callable#async task finished");
                return "async result";
            }
        };

        log.info("AsyncController#v1 finished");
        return callable;
    }

    @RequestMapping("/v2")
    public void v2(HttpServletRequest request, HttpServletResponse response){

        log.info("AsyncController#v2 called. Thread: {}",Thread.currentThread().getName());
        AsyncContext asyncContext = request.startAsync(request, response);

        Runnable runnable = () -> {
            log.info("controller-callable#v2 task started. Thread: " +
                    Thread.currentThread()
                            .getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                ServletResponse resp =  asyncContext.getResponse();
                PrintWriter writer = resp.getWriter();
                writer.write("async result");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("controller-callable#v2 task finished");
            asyncContext.complete();

        };
        executorService.execute(runnable);
        log.info("AsyncController#v2 finished");
    }
}
