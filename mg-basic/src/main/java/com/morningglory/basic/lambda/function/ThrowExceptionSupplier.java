package com.morningglory.basic.lambda.function;

/**
 * @author qianniu
 * @date 2021/1/6 1:03 下午
 * @desc
 */
@FunctionalInterface
public interface ThrowExceptionSupplier<T> {

    T get() throws Exception;
}
