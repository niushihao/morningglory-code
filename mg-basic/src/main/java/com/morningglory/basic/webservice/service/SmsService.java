package com.morningglory.basic.webservice.service;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 10:54
 * @Desc:
 */
@WebService(name ="SmsService",targetNamespace = "http://service.nsh.com")
public interface SmsService {

    @WebMethod
    @WebResult(name = "String",targetNamespace = "")
    String sendSms(@WebParam(name = "username") String username);

    @WebMethod
    @WebResult(name = "Long",targetNamespace = "")
    Long getBalance(@WebParam(name = "id") Integer id);

    @WebMethod
    @WebResult(name = "SmsDTO",targetNamespace = "")
    String sendDTO(@WebParam(name = "dto") SmsDTO dto);
}
