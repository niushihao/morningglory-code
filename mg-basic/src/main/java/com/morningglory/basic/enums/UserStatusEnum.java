package com.morningglory.basic.enums;

import com.morningglory.basic.exception.ParameterException;

public enum UserStatusEnum {

    /**正常*/
    NORMAL,
    /**禁用的*/
    DISABLED,
    /**已删除的*/
    DELETED;

    /**
     * 判断参数合法性
     */
    public static boolean isValidName(String name) throws ParameterException {
        for(UserStatusEnum userStatusEnum : UserStatusEnum.values()){
            if(userStatusEnum.name().equals(name)){
                throw new ParameterException("123");

            }
        }
        return false;
    }
}
