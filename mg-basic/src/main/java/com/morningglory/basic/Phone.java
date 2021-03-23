package com.morningglory.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author qianniu
 * @date 2020/7/30 8:57 下午
 * @desc
 */
@Slf4j
public class Phone {

    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 手机号脱敏筛选正则
     */
    public static final String PHONE_BLUR_REGEX = "(\\d{3})\\d{4}(\\d{4})";

    /**
     * 手机号脱敏替换正则
     */
    public static final String PHONE_BLUR_REPLACE_REGEX = "$1****$2";

    static final String EMAIL = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";



    /**
     * 手机号格式校验
     * @param phone
     * @return
     */
    public static final boolean checkPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

    /**
     * 手机号脱敏处理
     * @param phone
     * @return
     */
    public static final String blurPhone(String phone) {
        boolean checkFlag = checkPhone(phone);
        if (!checkFlag) {
            throw new IllegalArgumentException("手机号格式不正确!");
        }
        return phone.replaceAll(PHONE_BLUR_REGEX, PHONE_BLUR_REPLACE_REGEX);
    }

    public static void main(String[] args) {
        String phone ="15012348879";
        boolean b = checkPhone(phone);
        String s = blurPhone(phone);
        log.info(b+"");
        log.info(s+"");

        String email = "zhangnero@@163.com";
        System.out.println("原邮箱： " + email);

        boolean matches = email.matches(EMAIL);
        log.info("email match = {}",matches);

        email = email.replaceAll("(^\\w)[^@]*(@.*$)", "$1****$2");
        log.info(email);
    }
}
