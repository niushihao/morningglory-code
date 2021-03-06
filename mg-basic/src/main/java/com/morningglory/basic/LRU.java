package com.morningglory.basic;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: nsh
 * @Date: 2018/8/19
 * @Description:
 */
public class LRU {

    private int capacity;
    private Map<Integer,Integer> cacheMap;

    public LRU(int capacity){
        this.capacity = capacity;
        this.cacheMap = new LinkedHashMap<Integer,Integer>(capacity,0.75f,true){
            // 定义put后的移除规则，大于容量就删除eldest
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        } else
            return -1;
    }
    public void set(int key, int value) {
        cacheMap.put(key, value);
    }

    public int size(){
        return cacheMap.size();
    }

    public static void main(String[] args) {
        LRU lru = new LRU(1);
        lru.set(1,1);
        lru.set(2,2);
        lru.set(3,3);
        System.out.println(lru.size());

    }
}