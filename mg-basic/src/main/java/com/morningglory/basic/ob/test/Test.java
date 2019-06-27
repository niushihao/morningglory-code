package com.morningglory.basic.ob.test;

import com.morningglory.basic.ob.test.sub.IoSubjectAble;
import com.morningglory.basic.ob.test.ob.IoObAble;

/**
 * @Author: nsh
 * @Date: 2018/8/16
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

        IoSubjectAble subject = new IoSubjectAble();
        subject.register(new IoObAble());

        subject.connect();

        subject.receive();
    }
}