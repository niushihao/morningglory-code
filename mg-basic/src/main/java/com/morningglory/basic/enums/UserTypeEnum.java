package com.morningglory.basic.enums;

/**
 * @Author: qianniu
 * @Date: 2019-03-08 14:21
 * @Desc:
 */
public enum  UserTypeEnum {

    boy("男的"),
    girl("女的");

    private String value;
    UserTypeEnum(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(UserTypeEnum.boy.value);
    }
}
