package com.morningglory.basic.webservice.at.com.ctc.ema.server.jwsserver.sms;

import com.morningglory.basic.webservice.ws.ISmsOperator;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2019-04-22T15:00:42.818+08:00
 * Generated source version: 3.2.5
 *
 */
@WebServiceClient(name = "SmsOperatorImpService",
                  wsdlLocation = "http://192.168.12.69:8080/smg/webService/smsOper?wsdl",
                  targetNamespace = "http://sms.jwsserver.server.ema.ctc.com/")
public class SmsOperatorImpService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://sms.jwsserver.server.ema.ctc.com/", "SmsOperatorImpService");
    public final static QName SmsOperatorImpPort = new QName("http://sms.jwsserver.server.ema.ctc.com/", "SmsOperatorImpPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.12.69:8080/smg/webService/smsOper?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SmsOperatorImpService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://192.168.12.69:8080/smg/webService/smsOper?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SmsOperatorImpService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SmsOperatorImpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SmsOperatorImpService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SmsOperatorImpService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SmsOperatorImpService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SmsOperatorImpService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns ISmsOperator
     */
    @WebEndpoint(name = "SmsOperatorImpPort")
    public ISmsOperator getSmsOperatorImpPort() {
        return super.getPort(SmsOperatorImpPort, ISmsOperator.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ISmsOperator
     */
    @WebEndpoint(name = "SmsOperatorImpPort")
    public ISmsOperator getSmsOperatorImpPort(WebServiceFeature... features) {
        return super.getPort(SmsOperatorImpPort, ISmsOperator.class, features);
    }

}
