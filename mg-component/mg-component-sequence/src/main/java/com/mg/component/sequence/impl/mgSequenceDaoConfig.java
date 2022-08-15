package com.mg.component.sequence.impl;

import com.mg.component.sequence.mgSequenceDao;
import lombok.Data;

import java.util.Objects;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
public  abstract class mgSequenceDaoConfig implements mgSequenceDao {

    public int step = 1000;
    public int retryTimes = 3;
    public boolean isAdjust = true;
    public String name;
    public String tableName = "sequence";
    public String nameColumnName = "name";
    public String valueColumnName = "value";
    public String updateColumnName = "updated_at";

    public String selectSql = "";
    public String updateSql = "";

    protected String getSelectSql(){
        if(Objects.isNull(selectSql)){
            StringBuilder buffer = new StringBuilder();
            buffer.append("select")
                    .append(valueColumnName)
                    .append("from")
                    .append(tableName)
                    .append("where")
                    .append(nameColumnName).append("?");
            this.selectSql = buffer.toString();
        }
        return selectSql;
    }

    protected String getInsertSql() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("insert into ").append(getTableName()).append("(");
        buffer.append(getNameColumnName()).append(",");
        buffer.append(getValueColumnName()).append(",");
        buffer.append(updateColumnName).append(") values(?,?,?);");
        return buffer.toString();
    }

    protected String getUpdateSql() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("update ").append(tableName);
        buffer.append(" set ").append(valueColumnName).append(" = ?, ");
        buffer.append(updateColumnName).append(" = ? where ");
        buffer.append(nameColumnName).append(" = ? and ");
        buffer.append(valueColumnName).append(" = ?");
        return buffer.toString();
    }

    protected abstract void init();
}
