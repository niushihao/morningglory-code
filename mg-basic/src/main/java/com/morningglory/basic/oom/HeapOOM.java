package com.morningglory.basic.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/12/28 11:34 下午
 * @desc
 * 1.-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *  新生代 10M 其中 Eden区8M
 *  老年代 10M
 * 2.-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/nsh/oom/heapdump.hprof
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while(true) {
            //TimeUnit.MILLISECONDS.sleep(1);
            list.add(new OOMObject());
        }
    }

}
