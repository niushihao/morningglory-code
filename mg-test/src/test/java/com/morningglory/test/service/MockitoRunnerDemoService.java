package com.morningglory.test.service;

import com.morningglory.test.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author qianniu
 * @date 2020/11/20 9:57 上午
 * @desc
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MockitoRunnerDemoService {

    @InjectMocks
    private DemoService demoService;

    @Mock
    private NameService nameService;

    @Mock
    private WelcomeService welcomeService;

    @Test
    public void hello(){
        String hello = demoService.hello();
        log.info(hello);
    }
}
