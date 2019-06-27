package com.morningglory.basic.reference;

import com.morningglory.basic.pojo.User;

/**
 * @Author: qianniu
 * @Date: 2019-02-02 11:35
 * @Desc:   测试值传递和引用传递
 */
public class ReferenceTest {

    public static void main(String[] args) {

        User user = new User();
        System.out.println("第一次 hashCode ="+user.hashCode());
        User newUser = changeUser(user);
        System.out.println("第三次 hashCode ="+newUser.hashCode());
        System.out.println("方法执行后 hashCode ="+user.hashCode());
    }

    private static User changeUser(User user) {

        user = new User();
        System.out.println("第二次 hashCode ="+user.hashCode());
        return user;
    }
}
