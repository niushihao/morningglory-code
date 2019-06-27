package com.morningglory.basic.webservice.service;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: qianniu
 * @Date: 2019-04-19 13:19
 * @Desc:
 */
@Data
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "infos")*/
class SmsDTO {

     //@XmlElement(name = "phoneNo")
     private String phoneNo;

     //@XmlElement(name = "context")
     private String context;
}
