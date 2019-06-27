package com.morningglory.basic.ob.test.sub;

import com.morningglory.basic.ob.test.ob.ObAble;

/**
 * 可被观察
 */
public interface SubjectAble {

    /**
     * 注册观察者
     * @param obAble
     */
    SubjectAble register(ObAble obAble);

    /**
     * 删除观察者
     * @param obAble
     */
    SubjectAble remove(ObAble obAble);
}
