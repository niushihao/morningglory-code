package com.morningglory.rpc.tomcat;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: qianniu
 * @Date: 2020-03-11 10:05
 * @Desc:
 */
@Data
public class DemoBean implements Serializable {

    private int code;
    private String msg;
}
