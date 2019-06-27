package com.morningglory.basic.ob;

/**
 * 抽象观察者
 */
public interface Observer<T> {

    void update(T t);
}
