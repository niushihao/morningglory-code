package com.morningglory.basic.collection;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: nsh
 * @Date: 2018/7/26
 * @Description:
 */
public class MapTest {

    public static class User{

        @Override
        public int hashCode() {
            return 1;
        }
    }

    public static void main(String[] args) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        ConcurrentHashMap<String,Integer> conMap = new ConcurrentHashMap();
        Object o = conMap.putIfAbsent("1", 1);
        System.out.println(conMap.get("1"));
        System.out.println(conMap);
        Integer put = conMap.putIfAbsent("1", 2);
        System.out.println("conMap ="+conMap+",put ="+put);

        Map map2 = Maps.newHashMap();
        map2.putIfAbsent("1",1);
        System.out.println(map2);


        Map<User,Integer> map1 = Maps.newHashMap();
        map1.put(new User(),1);
        map1.put(new User(),2);
        map1.put(new User(),3);
        map1.put(new User(),4);
        map1.put(new User(),5);
        map1.put(new User(),6);
        map1.put(new User(),7);
        map1.put(new User(),8);
        map1.get("");
        System.out.println(map1.size());
    }
}