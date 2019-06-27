package com.morningglory.basic.pojo;

import java.io.Serializable;

/**
 * @Author: nsh
 * @Date: 2018/6/27
 * @Description:
 */
public class BasePojo implements Serializable {

    private String appKey;

    private String token;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    void sayHi(){}


}