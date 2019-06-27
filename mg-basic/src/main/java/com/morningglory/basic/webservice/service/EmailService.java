package com.morningglory.basic.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 11:26
 * @Desc:
 */
@WebService(name ="EmailService",targetNamespace = "http://service.nsh.com")
public interface EmailService {

    @WebMethod
    @WebResult(name = "String",targetNamespace = "")
    String sendEmail(@WebParam(name = "username") String username);
}
