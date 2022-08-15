package com.morningglory.basic.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qianniu
 * @date 2022/6/13
 * @desc
 */
@Slf4j
public class TreeMapTest {

    public static void main(String[] args) {

        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("a",1);
        treeMap.put("b",2);
        treeMap.put("c",3);
        treeMap.put("d",4);
        String s = treeMap.firstKey();

        treeMap.get("");
        System.out.println(s);
        Iterator<Map.Entry<String, Integer>> iterator = treeMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            log.info("key ={},value ={}",next.getKey(),next.getValue());
        }

        Iterator<Map.Entry<String, Integer>> descIterator = treeMap.descendingMap().entrySet().iterator();
        while (descIterator.hasNext()){
            Map.Entry<String, Integer> next = descIterator.next();
            log.info("key ={},value ={}",next.getKey(),next.getValue());
        }
    }
}
