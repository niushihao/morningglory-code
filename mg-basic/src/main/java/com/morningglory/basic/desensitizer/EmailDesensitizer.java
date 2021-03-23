package com.morningglory.basic.desensitizer;

import org.springframework.util.StringUtils;

/**
 * @author qianniu
 * @date 2020/7/30 11:31 下午
 * @desc
 */
public class EmailDesensitizer implements Desensitizer{

    private String replaceLongStr = "***";
    private String replaceShortStr = "**";
    private String separator ="@";

    @Override
    public String desensitize(String message) {

        if(StringUtils.isEmpty(message)){
            return message;
        }

        String[] split = message.split(separator);
        if(split.length != 2){
            return message;
        }
        String prefix = split[0];
        String suffix = split[1];
        if(prefix.length() < 3){
            return prefix + replaceShortStr + separator + suffix;
        }
        return prefix.substring(0,3) + replaceLongStr + separator + suffix;
    }

}
