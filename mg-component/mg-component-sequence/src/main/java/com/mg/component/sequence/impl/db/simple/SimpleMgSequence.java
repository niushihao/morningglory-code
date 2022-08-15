package com.mg.component.sequence.impl.db.simple;

import com.mg.component.sequence.MgSequence;
import com.mg.component.sequence.mgSequenceDao;
import com.mg.component.sequence.MgSequenceRange;
import lombok.Data;

import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public class SimpleMgSequence implements MgSequence {

    private MgSequenceRange currentRange;
    private mgSequenceDao mgSequenceDao;
    private String name;
    private Lock lock = new ReentrantLock();


    @Override
    public long getNextVal() throws SQLException {
        if(Objects.nonNull(currentRange)){
            long value = currentRange.getAndIncrement();
            if(value >-1){
                return value;
            }
        }

        lock.lock();
        MgSequenceRange mgSequenceRange = mgSequenceDao.nextRange(name);
        this.currentRange = mgSequenceRange;
        lock.unlock();
        return currentRange.getAndIncrement();

    }
}
