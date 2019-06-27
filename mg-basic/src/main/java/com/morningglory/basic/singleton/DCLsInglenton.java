package com.morningglory.basic.singleton;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description: Double Check Locking 双检查锁机制
 */
public class DCLsInglenton {

    private static DCLsInglenton dcLsInglenton = null;

    private DCLsInglenton() {
    }

    public static DCLsInglenton getDcLsInglenton(){
        try {
           if(dcLsInglenton == null){
               //创建实例之前可能会有一些准备工作
               Thread.sleep(300);
               synchronized (DCLsInglenton.class){
                   if(dcLsInglenton == null){
                       //二次检车
                       dcLsInglenton = new DCLsInglenton();
                   }
               }
           }
        }catch (InterruptedException e){

        }
        return dcLsInglenton;
    }
}