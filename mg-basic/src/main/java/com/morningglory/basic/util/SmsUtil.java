package com.morningglory.basic.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: qianniu
 * @Date: 2019-04-23 19:24
 * @Desc:
 */
public class SmsUtil {

    /**
     * 将短信模板还原成数组
     *
     * @param templateContent
     * @return
     */
    public static List<String> translateSmsTemplateContent(String templateContent) {
        if (StringUtils.isEmpty(templateContent))
            return new ArrayList<>();

        List<String> replacement = new ArrayList<>();
        final Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}+");
        final Matcher matcher = pattern.matcher(templateContent);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                replacement.add(matcher.group(i));
            }
        }
        return replacement;
    }

    /**
     * 将发送内容根据模板替换成真实内容
     *
     * @param templateContent
     * @param sendContent
     * @return
     */
    public static String getSmsRealContent(String templateContent, List<String> sendContent) {
        List<String> replacement = translateSmsTemplateContent(templateContent);
        if (sendContent.size() != replacement.size()) {
            throw new RuntimeException();
        }
        for (int i = 0; i < replacement.size(); i++) {
            String re = "${" + replacement.get(i) + "}";
            templateContent = templateContent.replace(re, sendContent.get(i));
        }
        return templateContent;
        
    }

    public static void main(String[] args) {

        String temp = "验证码${code}，${name}您正在进行登录操作，若非本人操作，请勿泄露。";
        List<String> strings = translateSmsTemplateContent(temp);
        System.out.println(strings);

        String info = getSmsRealContent(temp, Lists.newArrayList("888888", "nsh"));
        System.out.println("info ="+info);

    }
}
