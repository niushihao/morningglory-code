package com.morningglory.basic.desensitizer;

import org.springframework.util.StringUtils;

/**
 * @author qianniu
 * @date 2020/7/30 11:25 下午
 * @desc
 */
public class PhoneDesensitizer implements Desensitizer{

    private String desensitizeRule = "(\\d{3})\\d{4}(\\d{4})";
    private String replaceStr = "$1****$2";

    @Override
    public String desensitize(String message) {
        if(StringUtils.isEmpty(message)){
            return message;
        }
        return message.replaceAll(desensitizeRule,replaceStr);
    }

}
