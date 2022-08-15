package com.mg.component.sequence.impl.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mg.component.sequence.MgSequence;
import com.mg.component.sequence.MgSequenceBuilder;
import com.mg.component.sequence.MgSequenceFactory;
import com.mg.component.sequence.impl.db.group.GroupMgSequence;
import com.mg.component.sequence.impl.db.group.GroupMgSequenceDao;
import com.mg.component.sequence.impl.db.simple.SimpleMgSequence;
import com.mg.component.sequence.impl.db.simple.SimpleMgSequenceDao;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
public class DbMgSequenceFactory implements MgSequenceFactory {


    @Override
    public MgSequence create(MgSequenceBuilder builder) {

        JSONObject result = JSON.parseObject(builder.getConfig());
        if (result == null) {
            return null;
        }

        // 数据库/redis/...
        String type = result.getString("type");
        if(!"db".equals(type)){
            return null;
        }

        // 单数据源/多数据源
        String mode = result.getString("mode");
        if("1".equals(mode)){
            return createSimpleSequence(builder);
        }else if("2".equals(mode)){
            return createGroupSequence(builder);
        }

        return null;
    }

    private MgSequence createGroupSequence(MgSequenceBuilder builder) {
        List<DataSource> dataSourceList = builder.getDataSourceList();

        // 从配置初始化dao
        GroupMgSequenceDao sequenceDao = new GroupMgSequenceDao();
        sequenceDao.setDataSources(builder.getDataSourceList());
        sequenceDao.setAdjust(true);
        sequenceDao.init();

        GroupMgSequence sequence = new GroupMgSequence();
        sequence.setName(builder.getName());
        sequence.setMgSequenceDao(sequenceDao);
        return sequence;
    }

    private MgSequence createSimpleSequence(MgSequenceBuilder builder) {
        DataSource dataSource = builder.getDataSourceList().get(0);

        // 从配置初始化dao
        SimpleMgSequenceDao sequenceDao = new SimpleMgSequenceDao();
        sequenceDao.setDataSource(dataSource);
        sequenceDao.setAdjust(true);
        sequenceDao.init();

        SimpleMgSequence sequence = new SimpleMgSequence();
        sequence.setName(builder.getName());
        sequence.setMgSequenceDao(sequenceDao);
        return sequence;
    }
}
