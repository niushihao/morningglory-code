package com.morningglory.mvc.controller;

import com.morningglory.mvc.aop.limiter.Limit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qianniu
 * @date 2020/8/17 2:27 下午
 * @desc
 */
@RestController
@RequestMapping("/limit")
@Slf4j
public class LimiterController {

    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();

    @Limit(key = "limitTest", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public void testLimiter1() {

        int i = ATOMIC_INTEGER_1.incrementAndGet();
        log.info("i ={}",i);

    }
}
