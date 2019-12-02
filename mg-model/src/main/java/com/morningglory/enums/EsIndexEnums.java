package com.morningglory.enums;


/**
 * @Author: qianniu
 * @Date: 2019-11-27 17:41
 * @Desc: 索引枚举
 */
public enum  EsIndexEnums {

    index_student("员工索引");


    private String desc;
    EsIndexEnums(String desc) {
        this.desc = desc;
    }

}
