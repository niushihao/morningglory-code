package com.morningglory.basic.concurrent;

/**
 * @Author: nsh
 * @Date: 2018/3/21
 * @Description:
 */
public class MyService {

    public MyList addServiceMethod(MyList myList,String data){
        try {
            synchronized(myList) {
                if (myList.getSize() < 1) {

                    Thread.sleep(2000);
                    myList.add(data);
                }
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return myList;
    }
}