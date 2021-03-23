package com.morningglory.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qianniu
 * @date 2020/11/18 3:46 下午
 * @desc
 */
@Component
//@Transactional
public class DemoService {

    @Autowired
    private NameService nameService;

    @Autowired
    private WelcomeService welcomeService;

    public String hello(){
        return "hello,"+welcomeService.welcome()+" "+nameService.name();
    }
}
