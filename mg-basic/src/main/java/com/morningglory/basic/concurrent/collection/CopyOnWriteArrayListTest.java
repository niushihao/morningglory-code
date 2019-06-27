package com.morningglory.basic.concurrent.collection;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: qianniu
 * @Date: 2019-02-01 16:38
 * @Desc:   写时复制一个新的数组，在新数组中进行增减，完成后将新数组的引用指向list，同时还会用可重入锁做并发控制
 */
@Slf4j
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {

        // 一个线程先读，另一个线程去写，验证读到的数据
        testGetThenAdd();

    }


    private static void testGetThenAdd() {

        //Integer.class.newInstance()
        CopyOnWriteArrayList<Integer> list =
                new CopyOnWriteArrayList(Lists.newArrayList(1,2,3));

        System.out.println("begin");
        Runnable readTask =() -> {
            CopyOnWriteArrayList selfList = list;
            log.info("{} 第一次读取list的大小为：{}",Thread.currentThread().getName(),selfList.size() );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("{} 第二次读取list的大小为：{}",Thread.currentThread().getName(),selfList.size() );
        };

        Runnable writeTask =() -> {

            list.add(4);

        };

        Thread read = new Thread(readTask);
        Thread write = new Thread(writeTask);
        read.start();
        write.start();
    }
}
