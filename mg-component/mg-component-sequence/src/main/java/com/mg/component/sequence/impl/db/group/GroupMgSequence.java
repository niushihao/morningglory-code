package com.mg.component.sequence.impl.db.group;

import com.mg.component.sequence.MgSequence;
import com.mg.component.sequence.mgSequenceDao;
import com.mg.component.sequence.MgSequenceRange;
import lombok.Data;

import java.sql.SQLException;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public class GroupMgSequence implements MgSequence {
    private mgSequenceDao mgSequenceDao;
    private MgSequenceRange currentRange;
    private String name;

    @Override
    public long getNextVal() throws SQLException {

        if(Objects.nonNull(currentRange)){
            long value = currentRange.getAndIncrement();
            if(value > -1){
                return value;
            }
        }
        MgSequenceRange mgSequenceRange = mgSequenceDao.nextRange(name);
        this.currentRange = mgSequenceRange;
        return currentRange.getAndIncrement();
    }

    public void init(){
        GroupMgSequenceDao groupSequenceDao = (GroupMgSequenceDao) mgSequenceDao;
        for(int i = 0; i< groupSequenceDao.getRetryTimes(); i++){
            groupSequenceDao.adjust(name);
        }
    }
}
