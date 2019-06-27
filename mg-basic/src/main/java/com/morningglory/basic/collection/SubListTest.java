package com.morningglory.basic.collection;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-02-01 16:07
 * @Desc:   理解subList 是 原 list 的视图
 *          其实就是在原list上操作的，每次操作的时候根据 offect计算出在原列表的位置
 *          虽然subList的API在操作后都会同步modCount,但是如果同时操作原列表时会出现编发错误
 */
public class SubListTest {

    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9);

        List<Integer> subList = list.subList(1, 2);
        subList.add(10);

        System.out.println("subList ="+subList);
        System.out.println("list ="+list);
    }
}
