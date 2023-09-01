package com.morningglory.basic.webservice.client;


import com.morningglory.basic.webservice.service.SmsService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//import org.junit.Test;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 11:15
 * @Desc:
 */
public class WSTestService {

   // @Test
    public void testSendSms(){

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/services/SmsService?wsdl");

        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));

        Object[] objects = new Object[0];
        try {

            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("sendSms", "nsh");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

    }

   // @Test
    public void testSendSms1(){
        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(SmsService.class);
        bean.setAddress("http://localhost:8080/services/SmsService");
        SmsService smsService = (SmsService)bean.create();
        String result = smsService.sendSms("123");
        System.out.println(result);
    }

    /*@Test
    public void testSendDTO() throws IOException {

        JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(ISmsOperator.class);
        bean.setAddress("http://192.168.12.69:8080/smg/webService/smsOper?wsdl");
        ISmsOperator iSmsOperator = (ISmsOperator) bean.create();

        MtMessage mtMessage = new MtMessage();
        mtMessage.getPhoneNumber().add("18698586119");
        mtMessage.setContent("测试");
        MtMessageRes mtMessageRes =
                iSmsOperator.sendSms("dsjprojectsrm", MD5Util.getMD5String("srm@1234"), "1", mtMessage);

        System.out.println(JSON.json(mtMessageRes));
    }

    @Test
    public void testDTO2() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/services/SmsService?wsdl");
        Object person = Thread.currentThread().getContextClassLoader().loadClass("com.springboot.demo.webservice.service.SmsDTO").newInstance();
        Method m = person.getClass().getMethod("setName", String.class);
        m.invoke(person, "Joe Schmoe");
        Method m1 = person.getClass().getMethod("setCustomerID", String.class);
        Method m2 = person.getClass().getMethod("setItemID", String.class);
        Method m3 = person.getClass().getMethod("setQty", Integer.class);
        Method m4 = person.getClass().getMethod("setPrice", Double.class);
        m1.invoke(person, "C001");
        m2.invoke(person, "I001");
        m3.invoke(person, 100);
        m4.invoke(person, 200.00);

        client.invoke("addPerson", person);

    }

    @Test
    public void testYK(){

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://30.43.88.96:8080/smg/webService/smsOper?wsdl");

        Object[] objects = new Object[2];
        try {

            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getBalance", "nsh","端点");
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testSendSMS(){

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://192.168.12.69:8080/smg/webService/smsOper");

        QName name = new QName("http://sms.jwsserver.server.ema.ctc.com","sendSms");
        String xmlStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sms=\"http://sms.jwsserver.server.ema.ctc.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <sms:sendSms>\n" +
                "         <arg0>1</arg0>\n" +
                "         <arg1>2</arg1>\n" +
                "         <arg2>3</arg2>\n" +
                "         <arg3>\n" +
                "            <!--Optional:-->\n" +
                "            <sendTime>1</sendTime>\n" +
                "            <!--Optional:-->\n" +
                "            <content>2</content>\n" +
                "            <!--Optional:-->\n" +
                "            <demo>3</demo>\n" +
                "            <!--Zero or more repetitions:-->\n" +
                "            <phoneNumber>4</phoneNumber>\n" +
                "            <!--Optional:-->\n" +
                "            <smsId>5</smsId>\n" +
                "            <!--Optional:-->\n" +
                "            <subCode>6</subCode>\n" +
                "         </arg3>\n" +
                "      </sms:sendSms>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        Object[] objects = new Object[2];
        try {

            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke(name, xmlStr);
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
    */

    //@Test
    public void sendSMS(){

       JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
        bean.setServiceClass(com.morningglory.basic.webservice.service.SmsService.class);
        bean.setAddress("http://127.0.0.1:8080/smg/webService/smsOper?wsdl");
        SmsService sendSms = (com.morningglory.basic.webservice.service.SmsService)bean.create();
        String result = sendSms.sendSms("123");

    }

}
