package com.morningglory.test.service;

import com.morningglory.test.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author qianniu
 * @date 2020/11/18 3:49 下午
 * @desc
 * 如果DemoService 是个代理类(有事务注解)，那么它的属性不会被mock，测试用例输出 hello,welcome nsh
 * 如果DemoService 不是代理类,此时属性mock才会生效，测试用例输出 hello,welcome test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class })
@Slf4j
public class SpringRunnerDemoService {

    @InjectMocks
    @Autowired
    private DemoService demoService;

    @Mock
    private NameService nameService;

    @Before
    public void myBefore() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(nameService.name()).thenReturn("test");
    }


    @Test
    public void hello(){
        String hello = demoService.hello();
        log.info(hello);
    }
}
