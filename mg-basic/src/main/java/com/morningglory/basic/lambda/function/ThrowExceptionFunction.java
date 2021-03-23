package com.morningglory.basic.lambda.function;

/**
 * @author qianniu
 * @date 2021/1/6 1:04 下午
 * @desc
 */
@FunctionalInterface
public interface ThrowExceptionFunction<T, R> {

    R apply(T t) throws Exception;
}
