package com.mg.component.sequence;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public class MgSequenceRange {

    private long min;
    private long max;

    private AtomicLong value;
    private volatile boolean over = false;

    public MgSequenceRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    public long getAndIncrement(){
        if(over){
            return -1;
        }

        long currentValue = value.getAndIncrement();
        if(currentValue > max){
            over = true;
            return -1;
        }

        return currentValue;
    }

    public void updateValue(long expect, long update){
        value.compareAndSet(expect,update);
    }
}
