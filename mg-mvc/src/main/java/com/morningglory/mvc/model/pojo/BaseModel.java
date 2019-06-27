package com.morningglory.mvc.model.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: qianniu
 * @Date: 2019-06-13 20:09
 * @Desc:
 */
@Data
public class BaseModel implements Serializable {

    /**主键*/
    private Long id;

    /**创建时间*/
    private Date createAt;

    /**更新时间*/
    private Date updateAt;

}
