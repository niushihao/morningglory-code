package com.morningglory.basic.ob.test.sub;

import com.morningglory.basic.ob.test.ob.ObAble;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/8/16
 * @Description:
 */
public abstract class AbstractSubjectAble implements SubjectAble{

    public List<ObAble> obList = new ArrayList<>();

    @Override
    public SubjectAble register(ObAble obAble){
        this.obList.add(obAble);
        return this;
    }

    @Override
    public SubjectAble remove(ObAble obAble){
        this.obList.remove(obAble);
        return this;
    }
}