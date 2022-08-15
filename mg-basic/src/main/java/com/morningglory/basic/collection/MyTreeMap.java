package com.morningglory.basic.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author qianniu
 * @date 2022/6/20
 * @desc
 */
@Slf4j
public class MyTreeMap<K extends Comparable,V> {

    MyTreeNode<K,V> root;
    public MyTreeMap() {
    }

    public V put(K k,V v){
        if(Objects.isNull(root)){
            root = new MyTreeNode<>(k, v,null);
            return null;
        }
        MyTreeNode<K,V> t = root;
        MyTreeNode<K,V> parent ;
        int compare;
        do{
            parent = t;
            Comparable requestKey = (Comparable) k;
            Comparable parentKey = (Comparable) parent.key;
            compare = requestKey.compareTo(parentKey) ;
            if(compare > 0){
                t = t.right;
            }else if(compare < 0){
                t = t.left;
            }else {
                log.info("key:{} ,parentKey :{}存在,更新返回",k,parent.getKey());
                return t.setValue(v);
            }
        }while (t != null);

        MyTreeNode<K, V> newTreeNode = new MyTreeNode<>(k, v,parent);
        if(compare > 0){
            parent.right = newTreeNode;
            log.info("k:{} ,parentKey :{},放入右边",k,parent.getKey());
        }else {
            parent.left = newTreeNode;
            log.info("k:{},parentKey :{} ,放入左边",k,parent.getKey());
        }
        return null;
    }

    public V get(K k){
        MyTreeNode<K,V> t = root;
        while (t != null){
            int compare = k.compareTo(t.key);
            if(compare > 0){
                t = t.right;
            }else if(compare < 0){
                t = t.left;
            }else {
                log.info("getKey :{},value = {}",k,t.value);
                return t.value;
            }
        }
        return null;
    }

    public MyTreeNode getNode(K k){
        MyTreeMap.MyTreeNode t = root;
        while (t != null){
            if(k.compareTo(t.key) > 0){
                t = t.right;
            }else if(k.compareTo(t.key) < 0){
                t = t.left;
            }else {
                return t;
            }
        }
        return null;
    }

    public V remove(K k){

        MyTreeNode node = getNode(k);
        if(Objects.isNull(node)){
            return null;
        }
        MyTreeNode parent = node.parent;
        MyTreeNode left = node.left;
        MyTreeNode right = node.right;
        if(Objects.nonNull(parent)){

        }
        return null;
    }


    public class MyTreeNode<K extends Comparable,V>{
        K key;
        V value;
        MyTreeNode<K,V> left;
        MyTreeNode<K,V> right;
        MyTreeNode<K,V> parent;

        public MyTreeNode(K key, V value,MyTreeNode<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public K getKey(){
            return key;
        }
        public V setValue(V v){
            V oldValue = value;
            value = v;
            return oldValue;
        }
    }

    public static void main(String[] args) {

        MyTreeMap myTreeMap = new MyTreeMap();
        myTreeMap.put(5,5);
        myTreeMap.put(3,3);
        myTreeMap.put(7,7);
        myTreeMap.put(2,2);
        myTreeMap.put(8,8);
        myTreeMap.put(7,7);

        Object o = myTreeMap.get(8);
    }
}
