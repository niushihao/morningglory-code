package com.morningglory.basic.timeWheel;

/**
 * @author qianniu
 * @date 2020/8/7 12:48 下午
 * @desc
 */
public class TimedTask {

    /**
     * 过期时间戳
     */
    private long expireTimestamp;

    /** 任务 */
    private Runnable task;

    /** 是否取消 */
    private volatile boolean cancle;

    protected Bucket bucket;

    protected TimedTask next;

    protected TimedTask pre;

    public String desc;

    public TimedTask(long expireTimestamp, Runnable task) {
        this.expireTimestamp = expireTimestamp;
        this.task = task;
        this.cancle = false;
    }

    public void canal(){
        cancle = true;
    }

    public boolean isCancle(){
        return cancle;
    }

    public Runnable getTask(){
        return task;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }
}
