package com.mg.component.sequence.impl.db.group;

import com.mg.component.sequence.MgSequenceRange;
import com.mg.component.sequence.impl.mgSequenceDaoConfig;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public class GroupMgSequenceDao extends mgSequenceDaoConfig {

    private int dscount = 2;
    private int outStep = dscount * step;
    private List<DataSource> dataSources;
    @Override
    public MgSequenceRange nextRange(String name) throws SQLException {

        for(int i =0; i< retryTimes; i++){

            for(int j = 0; j< dataSources.size(); j++){
                try {
                    DataSource dataSource = dataSources.get(j);
                    if(Objects.isNull(dataSource)){
                        continue;
                    }
                    Connection connection = dataSource.getConnection();
                    // 配合LAST_INSERT_ID 使用,可以返回 LAST_INSERT_ID 包含字段的值,也就是直接返回value的值
                    PreparedStatement preparedStatement = connection.prepareStatement(getFetchSql(j), Statement.RETURN_GENERATED_KEYS);

                    long newValue;
                    int updateCount = preparedStatement.executeUpdate();
                    if(updateCount ==1){
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            newValue = generatedKeys.getLong(1);

                            return new MgSequenceRange(newValue+1,newValue+step);
                        } else {

                        }
                    }
                }finally {
                    // 关闭资源
                }
            }
        }
        return null;
    }

    public void init(){
        if(dataSources.size() > dscount){
            dscount = dataSources.size();
        }else {
            for (int i = dataSources.size(); i < dscount; i++) {
                // 占位符
                dataSources.add(null);
            }
        }

        outStep = step * dscount;
    }

    /**
     *UPDATE sequence
     *    SET value= IF((value+ 2000)  % 2000!= 1 * 1000, LAST_INSERT_ID(value+ 2000 -(value+ 2000)  % 2000+ 2000+ 1 * 1000), LAST_INSERT_ID(value+ 2000)),
     *        gmtModified= NOW()  WHERE name= 'xxx'
     *
     * 这里的 value+ 2000 -(value+ 2000)  % 2000+ 2000+ 1 * 1000 目前还没看明白作用
     *
     * @param index
     * @return
     */
    private String getFetchSql(final int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(getTableName());
        sb.append(" SET ").append(getValueColumnName()).append(" = IF((");
        sb.append(getValueColumnName()).append(" + ").append(outStep).append(") % ").append(outStep);
        sb.append(" != ").append(index).append(" * ").append(step).append(", ");
        sb.append("LAST_INSERT_ID(");
        if (isAdjust()) {
            sb.append(getValueColumnName()).append(" + ").append(outStep).append(" - (");
            sb.append(getValueColumnName()).append(" + ").append(outStep).append(") % ");
            sb.append(outStep).append(" + ").append(outStep).append(" + ");
            sb.append(index).append(" * ").append(step);
        } else {
            sb.append(0);
        }
        sb.append("), LAST_INSERT_ID(").append(getValueColumnName()).append(" + ").append(outStep).append(")), ");
        sb.append(updateColumnName).append(" = NOW()");
        sb.append(" WHERE ").append(getNameColumnName()).append(" = ?");
        return sb.toString();
    }

    public static void main(String[] args) {
        long value = 0;
        for(int i=0;i<3;i++){
            value = getValue(value,i);
            System.out.println(value);
        }

    }

    private static long getValue(long oldValue,int index){
        long newValue ;
        if((oldValue + 2000) % 2000 != 1000 * index){
            newValue = (oldValue + 2000) - (oldValue +2000) % 2000 + 2000 + 1000 * index;
        }else {
            newValue = oldValue + 2000;
        }
        return newValue;
    }
    public void adjust(String name){
        if(!isAdjust){
            return;
        }

        Connection connection;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        for(int i=0;i<dataSources.size();i++){
            DataSource dataSource = dataSources.get(i);
            // 占位符
            if(Objects.isNull(dataSource)){
                continue;
            }

            try {
                connection = dataSource.getConnection();
                stmt = connection.prepareStatement(getSelectSql());
                stmt.setString(1, name);
                rs = stmt.executeQuery();
                int item = 0;
                while (rs.next()){
                    item++;
                    long val = rs.getLong(valueColumnName);

                    // 校验初始值,这里是为了处理数据源增加或减少的场景
                    if(!check(i,val)){
                        this.adjustUpdate(i, val, name);
                    }

                }
                // 不存在记录,自动插入
                if(item == 0){
                    adjustInner(i,name);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {

            }
        }

    }

    // 这个应该一直返回true吧
    private boolean check(int index,long value){
        return (value % outStep) == (index * step);
    }

    // 数据不存在,创建一条
    private void adjustInner(int i, String name) {
        DataSource dataSource = dataSources.get(i);
        long newValue = i * step;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getInsertSql());
            stmt.setString(1, name);
            stmt.setLong(2, newValue);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
        }
    }

    // 数据存在但数据不争取,自动调整
    private void adjustUpdate(int index, long value, String name) {
        // 设置成新的调整值
        long newValue = (value - value % outStep) + outStep + index * step;
        DataSource dataSource = dataSources.get(index);
        Connection conn = null;
        PreparedStatement stmt = null;
        // ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(getUpdateSql());
            stmt.setLong(1, newValue);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setString(3, name);
            stmt.setLong(4, value);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
        }
    }

}
