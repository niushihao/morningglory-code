package com.morningglory.basic.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author: qianniu
 * @Date: 2020-03-06 22:02
 * @Desc:
 * 函数式编程四大核心接口
 * 1.Function<T, R>     T：入参类型，R：出参类型
 * 2.Consumer<T>        T：入参类型；没有出参
 * 3.Supplier<T>        T：出参类型；没有入参
 * 4.Predicate<T>       T：入参类型；出参类型是Boolean
 *
 */
public class FunctionDemo {

    public static void main(String[] args) {

        // Function  T：入参类型，R：出参类型
        Function<Integer,Integer> function = x -> x * 10;
        function.apply(10);

        // Consumer T：入参类型，没有出参
        Consumer<Integer> consumer = x -> System.out.print(x);
        consumer.accept(1);

        // Supplier T：出参类型；没有入参
        Supplier<Integer> supplier = () -> 10;
        supplier.get();

        // Predicate  T：入参类型；出参类型是Boolean
        Predicate<Integer> predicate = x -> x >10;
        predicate.test(5);
    }
}
