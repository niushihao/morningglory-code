package com.morningglory.mvc.dao;

import com.morningglory.model.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 11:37
 * @Desc:
 */
@Mapper
public interface ItemDao {

    /**
     * 新增商品
     * @param item
     * @return
     */
    Integer add(Item item);
    Integer batchAdd(List<Item> items);

    /**
     * 购买商品
     * @param id
     * @param number
     * @return
     */
    Integer buy(@Param("id") Long id, @Param("number") Long number);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Item findById(Long id);

    /**
     * 更新商品信息
     * @param info
     * @return
     */
    Integer update(Item info);
}
