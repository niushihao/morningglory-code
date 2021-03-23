package com.morningglory.test.service;

import org.springframework.stereotype.Component;

/**
 * @author qianniu
 * @date 2020/11/18 3:45 下午
 * @desc
 */
@Component
public class WelcomeService {

    public String welcome(){
        return "welcome";
    }
}
