package com.morningglory.basic.reflection;

import com.morningglory.basic.pojo.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/6/27
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        Class<User> c =User.class;
        //全路径名
        /*System.out.println("c.getName():"+ c.getName());
        //类名称
        System.out.println("c.getSimpleName():"+c.getSimpleName());
        //this中所有属性
        System.out.println("c.getDeclaredFields():"+Arrays.asList(c.getDeclaredFields()).toString());
        //this和parent中所有public属性
        System.out.println("c.getFields():"+Arrays.asList(c.getFields()).toString());
        //this中所有方法
        System.out.println("c.getDeclaredMethods():"+Arrays.asList(c.getDeclaredMethods()).toString());*/
        //this和parent中所有public 方法
        System.out.println("c.getMethods():"+Arrays.asList(c.getMethods()));

        String.valueOf(1);

        Field field = c.getDeclaredField("lists");

        User user = c.newInstance();
        System.out.println(123);
        field.setAccessible(true);

        List list = new ArrayList();
        field.set(user,list);

        System.out.println("user.getList ="+user.getLists());
        //System.out.println("field.get ="+field.(user));

        Method setId = User.class.getMethod("setId", new Class[]{int.class});
        System.out.println(setId.toGenericString());



    }
}