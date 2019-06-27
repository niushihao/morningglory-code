package com.morningglory.basic.lambda;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-04-08 10:59
 * @Desc:
 */
public class groupByTest {

    public static void main(String[] args) {

        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        // 按照名称分组
        Map<String, List<String>> groupByName = items.stream().collect(Collectors.groupingBy(Function.identity()));
        System.out.println("groupByName = "+groupByName);

        // 按照名称分组，并统计每个名称的数量
        Map<String, Long> groupByNameAndCount = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("groupByNameAndCount = "+groupByNameAndCount);

        //按员列表顺序进行分组
        LinkedHashMap<String, List<String>> groupByNameLinked = items.stream().collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.toList()));
        System.out.println("groupByNameLinked = "+groupByNameLinked);

        // https://zacard.net/2016/03/17/java8-list-to-map/

    }
}
