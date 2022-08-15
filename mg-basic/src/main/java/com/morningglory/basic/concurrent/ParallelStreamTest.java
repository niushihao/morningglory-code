package com.morningglory.basic.concurrent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qianniu
 * @date 2022/3/9
 * @desc
 */
public class ParallelStreamTest {

    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);

        List<Object> collect = list.parallelStream().map(id -> getById(id)).collect(Collectors.toList());
        System.out.println(collect.size());
    }

    private static Object getById(Integer id) {
        try {
            if(id.equals(9)){
                throw new RuntimeException("参数错误");
            }
            return id;
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName()+": 查询失败。。。");
            return id;
        }

    }
}
