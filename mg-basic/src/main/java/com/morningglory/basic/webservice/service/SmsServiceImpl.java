package com.morningglory.basic.webservice.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 10:58
 * @Desc:
 */
@WebService(serviceName = "SmsService",targetNamespace = "http://service.nsh.com")
@Service
public class SmsServiceImpl implements SmsService{
    @Override
    public String sendSms(String username) {
        return "hello "+ username;
    }

    @Override
    public Long getBalance(Integer id) {
        return 500L;
    }

    @Override
    public String sendDTO(SmsDTO dto) {
        return dto.getPhoneNo();
    }


}
