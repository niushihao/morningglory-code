package com.morningglory.basic.factory;

import com.morningglory.basic.pojo.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: nsh
 * @Date: 2018/4/18
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        Factory factory = new FoodFactory();
        factory.getFactory().created();

        List<User> list = new ArrayList<>();
        for(int i =0;i<10;i++){
            User user = new User(i);
            list.add(user);
        }
        list.add(new User(1));

        ArrayList<User> collect = list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(User::getId))), ArrayList::new));

        System.out.println(collect.size());

    }

}