package com.morningglory.mvc.model.request;

import lombok.Data;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 12:38
 * @Desc:
 */
@Data
public class ItemBuyRequest {

    /**商品ID*/
    private Long itemId;

    /**购买数量*/
    private Long number;
}
