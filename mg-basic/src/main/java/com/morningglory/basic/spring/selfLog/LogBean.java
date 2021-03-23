package com.morningglory.basic.spring.selfLog;

import com.morningglory.basic.spring.autoconfig.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qianniu
 * @date 2020/6/24 3:37 下午
 * @desc
 */
@Service
public class LogBean {

    @Autowired
    private LogBean logBean;

    @Log
    public String getMsg(){
        return "logBean";
    }
}
