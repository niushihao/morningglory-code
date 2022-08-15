package com.mg.component.sequence.impl.db.simple;

import com.mg.component.sequence.MgSequenceRange;
import com.mg.component.sequence.impl.mgSequenceDaoConfig;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public class SimpleMgSequenceDao extends mgSequenceDaoConfig {
    private DataSource dataSource;
    private Lock lock = new ReentrantLock();

    @Override
    public MgSequenceRange nextRange(String name) throws SQLException {

        // todo 关闭资源
        for(int i=0;i< retryTimes;i++){
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getSelectSql());
            preparedStatement.setString(1,name);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long oldValue = resultSet.getLong(1);
            long newValue = oldValue + getStep();

            PreparedStatement updateStatement = connection.prepareStatement(getUpdateSql());
            updateStatement.setLong(1,newValue);
            updateStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            updateStatement.setString(3, name);
            updateStatement.setLong(4, oldValue);
            int affectedRows = updateStatement.executeUpdate();
            if(affectedRows <= 0){
                continue;
            }

            MgSequenceRange mgSequenceRange = new MgSequenceRange(oldValue + 1, newValue);
            return mgSequenceRange;
        }


        return null;
    }

    @Override
    public void init() {

    }
}
