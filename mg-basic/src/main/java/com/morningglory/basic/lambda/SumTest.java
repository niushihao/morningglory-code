package com.morningglory.basic.lambda;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-03-21 13:51
 * @Desc: 求和的测试;目前不知道哪种方式最好
 */
public class SumTest {

    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6);

        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        System.out.println("reduce = "+reduce.get());

        Integer collect = list.stream().collect(Collectors.summingInt(x -> x));
        System.out.println("collect = "+collect);

        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println("sum ="+sum);


    }
}
