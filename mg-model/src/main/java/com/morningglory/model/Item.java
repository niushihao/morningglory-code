package com.morningglory.model;

import lombok.Data;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 11:32
 * @Desc:
 */
@Data
public class Item extends BaseModel{

    /**商品编号*/
    private String itemCode;

    /**商品名称*/
    private String itemName;

    /**商品价格*/
    private Long price;

    /**商品数量*/
    private Long number;
}
