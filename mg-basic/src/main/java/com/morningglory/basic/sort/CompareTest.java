package com.morningglory.basic.sort;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/8/30
 * @Description:
 */
public class CompareTest {

    public static void main(String[] args) {

        stringSort();

        poSort();
    }

    private static void poSort() {

        Po p1 = new Po(1,"2018-08-02","2018-08");
        Po p2 = new Po(2,"2017-12-03","2017-12");
        Po p3 = new Po(3,"2018-04-07","2018-04");
        Po p4 = new Po(4,"2018-06-12","2018-06");
        Po p5 = new Po(5,"2018-04-05","2018-04");
        Po p6 = new Po(1,"2018-04-05","2018-04");

        List<Po> list = Lists.newArrayList(p1,p2,p3,p4,p5,p6);

        list.sort(Comparator.comparing(Po::getDate).thenComparing(Po::getCode));
        System.out.println(list);

        list.sort(Comparator.comparing(x -> x.getDate().substring(0,7)));
        list.sort((x,y) -> x.getDate().substring(0,7).compareTo(y.getDate().substring(0,7)));
        System.out.println(list);

    }

    private static void stringSort() {

        List<String> list = Lists.newArrayList("2018-01-01","2018-02-02","2018-01-03");
        list.sort((x,y) -> x.compareTo(y));
        System.out.println(list);
    }
}