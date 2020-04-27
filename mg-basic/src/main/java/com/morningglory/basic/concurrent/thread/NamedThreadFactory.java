package com.morningglory.basic.concurrent.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: qianniu
 * @Date: 2019-12-10 09:56
 * @Desc:
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final String DEFAULT_NAME = "canal-worker";
    private final String name;
    private final boolean daemon;
    private final ThreadGroup group;
    private final AtomicInteger threadNumber;
    private final Integer totalSize;

    public NamedThreadFactory() {
        this(DEFAULT_NAME);
    }

    public NamedThreadFactory(String name) {
        this(name,true,0);
    }

    public NamedThreadFactory(String name, int totalSize) {
        this(name,true,totalSize);
    }

    public NamedThreadFactory(String name, boolean daemon,int totalSize) {
        this.threadNumber = new AtomicInteger(0);
        this.name = name;
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.totalSize = totalSize;
    }



    @Override
    public Thread newThread(Runnable r) {
        String name = this.name + "-" + this.threadNumber.getAndIncrement();
        if(totalSize > 0){
            name = name + "-" + totalSize;
        }
        Thread thread = new Thread(this.group, r, name, 0L);
        thread.setDaemon(this.daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

    /**
     * 使用自定义规则的名称
     * @param selfName
     * @param r
     * @return
     */
    public Thread newThread(String selfName,Runnable r){
        Thread thread = new Thread(this.group, r, selfName, 0L);
        thread.setDaemon(this.daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

    public static void main(String[] args) {
        NamedThreadFactory factory= new NamedThreadFactory("test");
        for(int i =0; i<10; i++){
            Thread thread = factory.newThread(() -> {
            });
            System.out.println(thread.getName());
        }

    }
}
