package com.morningglory.h2.simple.store;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryVersionedMap<K,V> {

    public static volatile AtomicLong currentVersion = new AtomicLong();

    private Map<K, List<MemoryVersionedValue>> data = new ConcurrentHashMap<>();


    public V get(Transaction transaction ,K key) {

        List<MemoryVersionedValue> values = data.get(key);
        if(CollectionUtils.isEmpty(values)){
            return null;
        }

        // 逆序查找, 找到第一个小于等于事务id的版本
        for (int i = values.size()-1; i >= 0; i--) {
            MemoryVersionedValue memoryVersionedValue = values.get(i);
            if(memoryVersionedValue.getVersion() <= transaction.getTransactionId()){
                if(memoryVersionedValue.isTombstone()){
                    return null;
                }
                return (V) memoryVersionedValue.getValue();
            }
        }
        return null;
    }

    public void put(Transaction transaction ,K key, V value) {
        List<MemoryVersionedValue> values = data.get(key);
        if(CollectionUtils.isEmpty(values)){
            values = new java.util.ArrayList<>();
            data.put(key,values);
        }
        MemoryVersionedValue memoryVersionedValue = new MemoryVersionedValue();
        memoryVersionedValue.setValue(value);
        memoryVersionedValue.setVersion(transaction.getTransactionId());
        values.add(memoryVersionedValue);
    }

    public void delete(Transaction transaction ,K key) {
        List<MemoryVersionedValue> values = data.get(key);
        if(CollectionUtils.isEmpty(values)){
            return;
        }
        MemoryVersionedValue memoryVersionedValue = new MemoryVersionedValue();
        memoryVersionedValue.setTombstone(true);
        memoryVersionedValue.setVersion(transaction.getTransactionId());
        values.add(memoryVersionedValue);
    }
}
