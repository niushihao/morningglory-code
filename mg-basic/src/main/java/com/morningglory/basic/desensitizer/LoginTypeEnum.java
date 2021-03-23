package com.morningglory.basic.desensitizer;

import lombok.Data;
import lombok.Getter;

/**
 * @author qianniu
 * @date 2020/7/30 9:30 下午
 * @desc
 */
@Getter
public enum LoginTypeEnum {

    PHONE("^1[3-9]\\d{9}$",
            new PhoneDesensitizer()),
    EMAIL("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
            new EmailDesensitizer());

    // 类型规则
    private String rule;

    // 脱敏器
    private Desensitizer desensitizer;

    LoginTypeEnum(String rule,Desensitizer desensitizer) {
        this.rule = rule;
        this.desensitizer = desensitizer;
    }

    public static LoginTypeEnum getTypeByMessage(String message){
        for(LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()){
            if(message.matches(loginTypeEnum.rule)){
                return loginTypeEnum;
            }
        }
        return null;
    }
}
