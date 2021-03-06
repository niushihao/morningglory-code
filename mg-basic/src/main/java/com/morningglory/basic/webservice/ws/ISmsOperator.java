
package com.morningglory.basic.webservice.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ISmsOperator", targetNamespace = "http://sms.jwsserver.server.ema.ctc.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ISmsOperator {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns ReportMessageRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public ReportMessageRes getReport(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg5
     * @param arg4
     * @param arg1
     * @param arg0
     * @param arg6
     * @return
     *     returns MtMessageRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public MtMessageRes massBathSendSms(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1,
        @WebParam(name = "arg2", partName = "arg2")
        String arg2,
        @WebParam(name = "arg3", partName = "arg3")
        String arg3,
        @WebParam(name = "arg4", partName = "arg4")
        String arg4,
        @WebParam(name = "arg5", partName = "arg5")
        StringArray arg5,
        @WebParam(name = "arg6", partName = "arg6")
        String arg6);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns MtMessageRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public MtMessageRes sendSms(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1,
        @WebParam(name = "arg2", partName = "arg2")
        String arg2,
        @WebParam(name = "arg3", partName = "arg3")
        MtMessage arg3);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns MoMessageRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public MoMessageRes getSms(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns BalanceRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public BalanceRes getBalance(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns MtMessageRes
     */
    @WebMethod
    @WebResult(partName = "return")
    public MtMessageRes bathSendSms(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1,
        @WebParam(name = "arg2", partName = "arg2")
        String arg2,
        @WebParam(name = "arg3", partName = "arg3")
        MtMessageArray arg3);

}
