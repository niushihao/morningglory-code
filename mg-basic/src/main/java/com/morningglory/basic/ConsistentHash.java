package com.morningglory.basic;

import java.util.*;

/**
 * @author qianniu
 * @date 2023/9/1
 * @desc
 */
public class ConsistentHash<T> {

    // 节点的复制因子，也就是虚拟节点的个数
    private final int  numberOfReplicas;

    // 虚拟节点个数
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();// 存储虚拟节点的hash值到真实节点的映射

    public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;

    }

    public void add(T node){
        for(int i = 0; i< numberOfReplicas; i++){

            // 为虚拟节点计算不同的hash值，但是背后还是要路由到真实节点上
            String str = node.toString() + i;
            int hashCode = str.hashCode();
            System.out.println("add node haskCode = "+hashCode);
            circle.put(hashCode,node);
        }
    }

    public void remove(T node){
        for(int i = 0; i< numberOfReplicas; i++){
            String str = node.toString() + i;

            circle.remove(str.hashCode());
        }
    }

    public T get(Object key){
        if(circle.isEmpty()){
            return null;
        }

        int hashCode = key.hashCode();

        // 如果正好命中直接使用
        T t = circle.get(hashCode);
        if(Objects.nonNull(t)){
            return t;
        }

        // 否则取当前节点至尾节点的数据，取第一条就相当于顺时针取第一个，如果为空就都放在第一个节点
        SortedMap<Integer, T> tailMap = circle.tailMap(hashCode);

        hashCode = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        return circle.get(hashCode);
    }

    public long getSize(){
        return circle.size();
    }

    public void testBalance(){
        Set<Integer> sets = circle.keySet();//获得TreeMap中所有的Key
        SortedSet<Integer> sortedSets= new TreeSet<Integer>(sets);//将获得的Key集合排序
        for(Integer hashCode : sortedSets){
            System.out.println(hashCode);
        }

        System.out.println("----each location 's distance are follows: ----");
        /*
         * 查看相邻两个hashCode的差值
         */
        Iterator<Integer> it = sortedSets.iterator();
        Iterator<Integer> it2 = sortedSets.iterator();
        if(it2.hasNext())
            it2.next();
        long keyPre, keyAfter;
        while(it.hasNext() && it2.hasNext()){
            keyPre = it.next();
            keyAfter = it2.next();
            System.out.println(keyAfter - keyPre);
        }
    }

    public static void main(String[] args) {

        Set<String> hashSet = new HashSet<>();
        hashSet.add("A");
        hashSet.add("B");
        hashSet.add("C");

        ConsistentHash<String> consistentHash = new ConsistentHash(2, hashSet);

        consistentHash.add("D");

        System.out.println("hash circle size: " + consistentHash.getSize());
        System.out.println("location of each node are follows: ");
        consistentHash.testBalance();

        String node =consistentHash.get("apple");
        System.out.println("node----------->:"+node);
    }
}
