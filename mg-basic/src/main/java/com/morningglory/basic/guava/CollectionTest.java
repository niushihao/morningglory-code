package com.morningglory.basic.guava;

import com.google.common.collect.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: nsh
 * @Date: 2018/9/5
 * @Description:
 */
public class CollectionTest {

    public static void main(String[] args) {

        immutableSetTest();

        multisetTest();

        arrayListMultimapTest();

        hashMultimapTest();

        HashMap<String,Integer> map = Maps.newHashMap();
        map.put("1",1);
        map.merge("2",2,(k,v)->{return null;});
        map.forEach((k,v)->{
            System.out.println(k);
            System.out.println(v);
        });
    }

    private static void hashMultimapTest() {

        HashMultimap multimap = HashMultimap.create();
        multimap.put("a",12);
        multimap.put("a",13);
        multimap.put("a",14);
        System.out.println("hashMultimapTest ="+multimap);
        System.out.println(multimap.get("b"));
    }

    private static void arrayListMultimapTest() {

        ArrayListMultimap multimap = ArrayListMultimap.create();
        multimap.put("a",12);
        multimap.put("a",12);
        multimap.put("a",12);
        System.out.println("arrayListMultimapTest ="+multimap);
        List b = multimap.get("b");
        System.out.println("b ="+b);

    }

    private static void multisetTest() {

        HashMultiset<String> set = HashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("b");
        set.add("c");
        System.out.println(set.toString());
        set.count("a");
    }

    private static void immutableSetTest() {

        ImmutableSet<String> set = ImmutableSet.of("1","2");
        System.out.println(set.size());

        ImmutableList<String> strings = set.asList();
        System.out.println(set);
        System.out.println(strings.reverse());
    }
}