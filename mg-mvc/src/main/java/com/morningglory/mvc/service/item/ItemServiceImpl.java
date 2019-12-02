package com.morningglory.mvc.service.item;

import com.morningglory.model.Item;
import com.morningglory.mvc.dao.ItemDao;
import com.morningglory.request.ItemBuyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 12:40
 * @Desc:
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService{

    @Resource
    private ItemDao itemDao;
    @Override
    public Integer add(Item item) {
        return itemDao.add(item);
    }

    @Override
    public Integer batchAdd(List<Item> items) {
        return itemDao.batchAdd(items);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean safeBuy(ItemBuyRequest request) {
        return itemDao.buy(request.getItemId(),request.getNumber()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unsafeBuy(ItemBuyRequest request) {

        // 根据ID查询商品详情
        Item item = itemDao.findById(request.getItemId());
        if(item.getNumber() < request.getNumber()){
            log.info("库存不足");
            return false;
        }

        Item info = new Item();
        info.setId(request.getItemId());
        info.setNumber(item.getNumber() - request.getNumber());
        itemDao.update(info);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addStock(Long itemId, Long number) {
        return itemDao.addNumber(itemId,number) > 0;
    }

    @Override
    public List<Item> list() {
        return itemDao.list();
    }


}
