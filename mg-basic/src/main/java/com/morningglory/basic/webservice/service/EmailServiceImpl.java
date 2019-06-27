package com.morningglory.basic.webservice.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 11:27
 * @Desc:
 */
@WebService(serviceName = "EmailService",targetNamespace = "http://service.nsh.com")
@Service
public class EmailServiceImpl implements EmailService{
    @Override
    public String sendEmail(String username) {
        return null;
    }
}
