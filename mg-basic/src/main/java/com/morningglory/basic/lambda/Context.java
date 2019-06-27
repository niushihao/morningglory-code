package com.morningglory.basic.lambda;

import com.google.common.collect.Lists;
import com.morningglory.basic.pojo.User;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-04-12 16:31
 * @Desc:
 */
public class Context {

    private List<User> list;

    public static List<User> getList() {

        List<User> list = Lists.newArrayList();
        for(int i = 0; i< 5; i++){
            User user = new User(i);
            list.add(user);
        }

        return list;
    }
}
