package com.morningglory.basic.collection;

import com.google.common.collect.Lists;
import org.springframework.util.StopWatch;

import java.util.Iterator;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-02-01 14:43
 * @Desc:   几种遍历的速度
 *
 * 一千万条数据
 * ms     %     Task name
 * -----------------------------------------
 * 00003  003%  for-index
 * 00008  007%  iterator-while
 * 00006  005%  iterator-for-eatch
 * 00021  019%  for-eatch
 * 00072  065%  stream-for-eatch
 */
public class LoopSpeed {

    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList();

        for(int i =0;i<100000;i++){
            list.add(i);
        }
        StopWatch watch = new StopWatch();

        watch.start("for-index");
        for(int i =0;i< list.size();i++){

        }
        watch.stop();

        watch.start("iterator-while");
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.next();
        }
        watch.stop();

        watch.start("iterator-for-eatch");
        for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
            it.next();
        }
        watch.stop();

        watch.start("for-eatch");
        for(Integer i : list){
        }
        watch.stop();

        watch.start("stream-for-eatch");
        list.forEach(i ->{});
        watch.stop();

        System.out.println(watch.prettyPrint());
    }
}
