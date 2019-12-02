package com.morningglory.page;

import lombok.Data;

/**
 * @Author: qianniu
 * @Date: 2019-06-24 15:31
 * @Desc:
 */
@Data
public class Page<T> {

    /**页数*/
    private int pageNo;

    /**每页条数*/
    private int pageSize;

    /**总页数*/
    private int total;

    private T result;
}
