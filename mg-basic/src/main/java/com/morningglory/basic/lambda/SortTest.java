package com.morningglory.basic.lambda;

import com.morningglory.basic.pojo.User;

import java.util.Comparator;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-04-12 16:15
 * @Desc:
 */
public class SortTest {

    public static void main(String[] args) {

        List<User> list = Context.getList();
        list.get(0).setId(null);

        //如果数据中有null 可以使用Comparator.nullsFirst/Comparator.nullsLast
        list.sort(Comparator.comparing(User::getId,Comparator.nullsFirst(Integer::compareTo)));
        list.forEach(u -> System.out.println(u.getId()));

        // 默认升序，改成j-i后可以变成降序
        list.sort((i,j) -> (j.getId() - i.getId()));

    }
}
