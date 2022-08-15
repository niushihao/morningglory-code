package com.morningglory.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:09
 * @Desc:
 */
@Data
public class Student extends BaseModel{

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 兴趣
     */
    private String interest;

    /**
     * 简介
     */
    private String desc;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 测试es日期类型
     */
    private LocalDateTime dateTime;
}
