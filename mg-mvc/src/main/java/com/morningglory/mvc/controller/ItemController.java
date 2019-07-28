package com.morningglory.mvc.controller;

import com.google.common.collect.Lists;
import com.morningglory.model.Item;
import com.morningglory.mvc.model.request.DecimalRequest;
import com.morningglory.mvc.model.request.ItemBuyRequest;
import com.morningglory.mvc.service.item.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author: qianniu
 * @Date: 2019-06-26 12:43
 * @Desc:
 */
@RestController
@RequestMapping("item")
@Slf4j
public class ItemController {

    @Resource
    private ItemService itemService;

    /**
     * 新增商品
     *
     * @return
     */
    @PostMapping("/add")
    public Boolean addItem() {
        Item item = new Item();
        item.setItemCode("ITEM1");
        item.setItemName("商品1");
        item.setNumber(10L);
        item.setPrice(10L);
        itemService.add(item);

        return true;
    }

    /**
     * 批量新增商品
     *
     * @return
     */
    @PostMapping("/batch/add")
    public Boolean batchAddItem() {

        List<Item> list = Lists.newArrayListWithCapacity(1);
        itemService.batchAdd(list);

        return true;
    }

    /**
     * 购买商品
     *
     * @return
     */
    @PostMapping("/buy")
    public Boolean buyItem() {

        ItemBuyRequest request = new ItemBuyRequest();
        request.setItemId(1L);
        request.setNumber(10L);
        log.info("{} 开始购买", Thread.currentThread().getName());

        //return itemService.safeBuy(request);
        return itemService.unsafeBuy(request);
    }

    /**
     * 秒杀
     *
     * @return
     */
    @PostMapping("/spike")
    public Boolean spike() {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        Executor executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {

            Runnable runnable = () -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                Boolean success = buyItem();
                if (success) {
                    log.info("用户 {} 秒杀成功", Thread.currentThread().getName());
                }

            };

            executor.execute(runnable);
        }


        return true;
    }

    @PostMapping("/add/stock")
    public Boolean addStock() {


        return itemService.addStock(1L, 1L);
    }

    /**
     * 并发增加库存
     * @return
     */
    @PostMapping("/add/stocks")
    public Boolean addStocks() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100);
        Executor executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {

            Runnable runnable = () -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                Boolean success = addStock();
                if (success) {
                    log.info("用户 {} 增加库存成功", Thread.currentThread().getName());
                }

            };

            executor.execute(runnable);

        }
        return true;
    }

    @PostMapping("/add/decimal")
    public Boolean addDecimal(@RequestBody DecimalRequest request){
        log.info("request = {}",request.getDecimal());
        return true;
    }
}