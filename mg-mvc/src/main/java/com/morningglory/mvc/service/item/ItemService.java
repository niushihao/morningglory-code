package com.morningglory.mvc.service.item;

import com.morningglory.mvc.model.pojo.Item;
import com.morningglory.mvc.model.request.ItemBuyRequest;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 11:51
 * @Desc:
 */
public interface ItemService {

    /**
     * 新增商品
     * @param item
     * @return
     */
    Integer add(Item item);
    Integer batchAdd(List<Item> items);

    /**
     * 购买商品(不会出现超卖)
     * @param request
     * @return
     */
    Boolean safeBuy(ItemBuyRequest request);

    /**
     * 购买商品(可能出现超卖)
     * @param request
     * @return
     */
    Boolean unsafeBuy(ItemBuyRequest request);


}
