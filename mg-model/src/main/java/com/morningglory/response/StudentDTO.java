package com.morningglory.response;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: qianniu
 * @Date: 2019-11-28 19:25
 * @Desc:
 */
public class StudentDTO {

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
