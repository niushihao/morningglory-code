package com.morningglory.basic.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/3/21
 * @Description: 自定义list
 */
public class MyList {

    private List list = new ArrayList();

    synchronized public void add(String data){
        list.add(data);
    }

    synchronized public int getSize(){
        return list.size();
    }
}