package com.morningglory.basic.concurrent.collection;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qianniu
 * @date 2020/6/10 10:43 下午
 * @desc
 */
@Slf4j
public class ConsurrentHashMapTest {

    public static void main(String[] args) {

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1);
        concurrentHashMap.put("key","valuex");
        concurrentHashMap.put("11","11");
        concurrentHashMap.get("");


        HashMap hashMap = new HashMap(2);
        hashMap.put("key","value");
        hashMap.put("11","11");

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("key","value");
        linkedHashMap.forEach((k,v)->{});

        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMapWithExpectedSize(8);
        log.info("objectObjectHashMap size = {}",objectObjectHashMap.size());
    }
}
