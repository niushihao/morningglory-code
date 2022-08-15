package com.morningglory.sharding.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author qianniu
 * @date 2022/7/5
 * @desc 读写分离
 */
@Slf4j
public class MasterSalveDemo {

    public static void main(String[] args) throws SQLException {

        // 获取数据源
        Map<String, DataSource> dataSourceMap= getDsConfig();

        // 配置读写分离规则
        MasterSlaveRuleConfiguration configuration = new MasterSlaveRuleConfiguration("ds_master_salve",
                "ds_master", Lists.newArrayList("ds_salve0", "ds_salve1"));
        Properties properties = new Properties();
        properties.setProperty("sql.show","true");
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, configuration, properties);

        String sql1 = "SELECT * FROM t_order   WHERE user_id=? ";

        for(int i=0; i<10;i++){
            try (
                    Connection conn = dataSource.getConnection();
                    PreparedStatement preparedStatement = conn.prepareStatement(sql1)) {
                preparedStatement.setInt(1, 10);
                //preparedStatement.setString(1, "test");
                //preparedStatement.setInt(2, 1001);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if(!rs.next()){
                        log.info("没有数据");
                    }
                    while(rs.next()) {
                        System.out.println(rs.getInt(1));
                        System.out.println(rs.getInt(2));
                    }
                }
            }
        }

    }

    private static Map<String, DataSource> getDsConfig() {

        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        // 主数据源
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        masterDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ds_master?characterEncoding=utf8&useSSL=false");
        masterDataSource.setUsername("root");
        masterDataSource.setPassword("123456");
        dataSourceMap.put("ds_master", masterDataSource);

        // 第一个从数据源
        DruidDataSource salve0DataSource = new DruidDataSource();
        salve0DataSource.setDriverClassName("com.mysql.jdbc.Driver");
        salve0DataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ds_salve0?characterEncoding=utf8&useSSL=false");
        salve0DataSource.setUsername("root");
        salve0DataSource.setPassword("123456");
        dataSourceMap.put("ds_salve0", salve0DataSource);

        // 第二个从数据源
        DruidDataSource salve1DataSource = new DruidDataSource();
        salve1DataSource.setDriverClassName("com.mysql.jdbc.Driver");
        salve1DataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ds_salve1?characterEncoding=utf8&useSSL=false");
        salve1DataSource.setUsername("root");
        salve1DataSource.setPassword("123456");
        dataSourceMap.put("ds_salve1", salve1DataSource);
        return dataSourceMap;
    }
}
