package com.morningglory.basic.lambda;

import com.google.common.collect.Lists;
import com.morningglory.basic.pojo.User;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-04-12 15:15
 * @Desc:
 */
public class FilterTest {

    public static void main(String[] args) {

        List<User> list = Lists.newArrayList();
        for(int i =0; i< 3; i++){
            User u = new User();
            u.setId(i);
            list.add(u);
        }

        User user = list.stream().filter(u -> u.getId() == 4).findFirst().orElse(null);


        System.out.println("user ="+user);
    }
}
