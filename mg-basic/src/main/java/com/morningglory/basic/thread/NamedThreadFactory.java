package com.morningglory.basic.thread;

import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: qianniu
 * @Date: 2019-12-10 09:56
 * @Desc:
 */
public class NamedThreadFactory implements ThreadFactory {
    private final static Map<String, AtomicInteger> PREFIX_COUNTER = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final String prefix;
    private final int totalSize;
    private final boolean makeDaemons;

    public NamedThreadFactory(String prefix, int totalSize, boolean makeDaemons) {
        PREFIX_COUNTER.putIfAbsent(prefix, new AtomicInteger(0));
        int prefixCounter = PREFIX_COUNTER.get(prefix).incrementAndGet();
        this.prefix = prefix + "_" + prefixCounter;
        this.totalSize = totalSize;
        this.makeDaemons = makeDaemons;
    }

    public NamedThreadFactory(String prefix, boolean makeDaemons) {
        this(prefix,0,makeDaemons);
    }

    public NamedThreadFactory(String prefix, int totalSize) {
        this(prefix,totalSize,true);
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + "_" + counter.incrementAndGet();
        if(totalSize > 1){
            name += "_" + totalSize;
        }
        Thread thread = new FastThreadLocalThread(r, name);
        thread.setDaemon(makeDaemons);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

    public static void main(String[] args) {
        NamedThreadFactory factory = new NamedThreadFactory("test",10,true);
        for(int i = 0; i< 20;i++){
            Thread thread = factory.newThread(() -> {
            });
            System.out.println(thread.getName());
        }

    }
}
