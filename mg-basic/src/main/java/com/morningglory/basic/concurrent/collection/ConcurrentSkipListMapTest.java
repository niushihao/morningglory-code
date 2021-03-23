package com.morningglory.basic.concurrent.collection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author qianniu
 * @date 2020/9/8 12:58 下午
 * @desc
 * @see : https://juejin.im/post/6844903958499033095
 *
 * 此容器为线程安全的跳表实现,主要分为三部分
 *
 * 1.Node 最底层的有序链表
 * static final class Node<K,V> {
 *     final K key;
 *     volatile Object value;
 *     volatile ConcurrentSkipListMap.Node<K, V> next;
 *  }
 *
 * 2.Index 链表的上层,也称为索引层
 * static class Index<K,V> {
 *     final ConcurrentSkipListMap.Node<K,V> node;
 *     final ConcurrentSkipListMap.Index<K,V> down;
 *     volatile ConcurrentSkipListMap.Index<K,V> right;
 *  }
 *
 * 3.HeadIndex 在索引的基础上增加了层级的概念
 * static final class HeadIndex<K,V> extends Index<K,V> {
 *     final int level;  //索引层，从1开始，Node单链表层为0
 *     HeadIndex(Node<K,V> node, Index<K,V> down, Index<K,V> right, int level) {
 *         super(node, down, right);
 *         this.level = level;
 *     }
 * }
 *
 * 查找元素时都先从层级最高的HeadIndex开始,先尝试向右找,如果不匹配或者不存在,在向下找,直到找到对应的Node
 */
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {

        ConcurrentSkipListMap skipListMap = new ConcurrentSkipListMap();
        skipListMap.put("str","str");
        skipListMap.get("str");
    }
}
