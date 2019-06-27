package com.morningglory.basic.webservice.config;


/**
 * @Author: qianniu
 * @Date: 2019-04-19 11:00
 * @Desc: 启动后访问 http://localhost:8080/services/SmsService?wsdl
 *         或者http://localhost:8080/services/EmailService?wsdl
 */
//@Configuration
public class CxfConfig {

  /*  @Resource
    private Bus bus;

    @Resource
    private SmsService smsService;

    @Resource
    private EmailService emailService;

    @Bean
    public Endpoint endpoint(){
        Endpoint smsEndpoint = new EndpointImpl(bus,smsService);
        Endpoint emailEndpoint = new EndpointImpl(bus,emailService);
        ((EndpointImpl) smsEndpoint).publish("/SmsService");
        *//*((EndpointImpl) emailEndpoint).publish("/EmailService");*//*
        return smsEndpoint;
    }*/
}
