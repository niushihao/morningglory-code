package com.morningglory.basic.webservice.client;

import com.morningglory.basic.util.MD5Util;
import com.morningglory.basic.webservice.ws.*;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.junit.Test;

/**
 * @Author: qianniu
 * @Date: 2019-05-05 18:48
 * @Desc:
 */
public class Axis2Test {

    @Test
    public void sendSms() throws AxisFault {

        try {
            ServiceClient serviceClient = new ServiceClient();
            //创建服务地址WebService的URL,注意不是WSDL的URL
            String url = "http://192.168.12.69:8080/smg/webService/smsOper";
            EndpointReference targetEPR = new EndpointReference(url);
            Options options = serviceClient.getOptions();
            options.setTo(targetEPR);
            //确定调用方法（wsdl 命名空间地址 (wsdl文档中的targetNamespace) 和 方法名称 的组合）
            options.setAction("http://sms.jwsserver.server.ema.ctc.com/sendSms");

            OMFactory fac = OMAbstractFactory.getOMFactory();
            /*
             * 指定命名空间，参数：
             * uri--即为wsdl文档的targetNamespace，命名空间
             * perfix--可不填
             *
             * OMElement method = factory.createOMElement("sendmsg", omNs);
                OMElement value1 = factory.createOMElement("in0", omNs);
                value1.addChild(factory.createOMText(value1, keyId));
             */
            OMNamespace omNs = fac.createOMNamespace("http://sms.jwsserver.server.ema.ctc.com/", "");
            // 指定方法
            OMElement method = fac.createOMElement("sendSms", omNs);
            // 指定方法的参数
            OMElement account = fac.createOMElement("arg0", omNs);
            account.addChild(fac.createOMText(account,"dsjprojectsrm"));
            //account.setText("dsjprojectsrm");

            OMElement password = fac.createOMElement("arg1", omNs);
            password.setText("srm@1234");


            OMElement type = fac.createOMElement("arg2", omNs);
            type.setText("");

            OMElement msg = fac.createOMElement("arg3", omNs);
            msg.addAttribute("content","短信测试",omNs);
            msg.addAttribute("phoneNumber","18698586119",omNs);

            method.addChild(account);
            method.addChild(password);
            method.addChild(type);
            method.addChild(msg);
            method.build();

            //远程调用web服务
            OMElement result = serviceClient.sendReceive(method);
            System.out.println(result);

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }


    @Test
    public void send(){

        SmsOperatorImpService smsOperatorImpService = new SmsOperatorImpService();
        ISmsOperator client =
                smsOperatorImpService.getSmsOperatorImpPort();


        ObjectFactory objectFactory = new ObjectFactory();
        MtMessage mtMessage = objectFactory.createMtMessage();
        mtMessage.setContent("ceshi");
        mtMessage.getPhoneNumber().add("18698586119");
        MtMessageRes mtMessageRes = client.sendSms("dsjprojectsrm", MD5Util.getMD5String("srm@123"), "1", mtMessage);

        System.out.println(mtMessageRes.getSubStatDes());
    }

}
