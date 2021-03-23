package com.morningglory.basic.desensitizer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/7/30 9:28 下午
 * @desc
 */
@Slf4j
public class SecurityTool {

    public static String desensitize(String loginId){

        if(StringUtils.isEmpty(loginId)){
            return loginId;
        }
        // 识别输⼊格式
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.getTypeByMessage(loginId);
        if(Objects.isNull(loginTypeEnum)){
            log.debug("loginId:{} 没有对应的脱敏器",loginId);
            return loginId;
        }

        // 进⾏脱敏处理
        log.debug("message:{} 进行{}类型脱敏",loginId,loginTypeEnum.name());
        return loginTypeEnum.getDesensitizer().desensitize(loginId);
    }

}
