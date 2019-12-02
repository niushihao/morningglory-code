package com.morningglory.request;

import com.morningglory.page.Page;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-11-28 19:28
 * @Desc:
 */
@Data
public class StudentSearchRequest extends Page {

    /**用户ids*/
    private List<Long> ids;

    /**用户名称*/
    private String name;

    /**年龄范围*/
    private int minAge;

    /**年龄范围*/
    private int maxAge;

    /**生日范围*/
    private Date startDate;

    /**生日范围*/
    private Date endDate;


}
