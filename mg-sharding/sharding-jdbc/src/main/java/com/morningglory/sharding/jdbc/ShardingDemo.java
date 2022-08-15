package com.morningglory.sharding.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author qianniu
 * @date 2022/5/17
 * @desc 基于Java编码的分库分表规则
 */
@Slf4j
public class ShardingDemo {

    public static void main(String[] args) throws SQLException {

        // 获取数据源
        Map<String, DataSource> dataSourceMap= getDsConfig();

        // 获取分片规则
        ShardingRuleConfiguration shardingConfig = getShardingConfig();

        Properties properties = new Properties();
        properties.setProperty("sql.show","true");
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingConfig, properties);

        // 根据分库键查询,会查询这个库下的所有表
        String sql1 = "SELECT * FROM t_order   WHERE user_id=? ";

        // 根据分表键查询,会查询所有库下匹配的表
        String sql2 = "SELECT * FROM t_order   WHERE order_id=? ";

        // sql中不包含分库键和分表键
        String sql3 = "SELECT * FROM t_order   WHERE name=? groyp by id";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql3)) {
            //preparedStatement.setInt(1, 10);
            preparedStatement.setString(1, "test");
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

    private static ShardingRuleConfiguration getShardingConfig() {
        // 配置order表的规则（两个库两个表）
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds_${0..1}.t_order${0..1}");

        // 配置分库策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id"
                ,"ds_${user_id % 2}"));

        // 配置分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id",
                "t_order${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(orderTableRuleConfig);
        return shardingRuleConfiguration;
    }

    private static Map<String, DataSource> getDsConfig() {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        // 第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://127.0.0.1:3306/ds_0?characterEncoding=utf8&useSSL=false");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ds_0", dataSource1);

        // 第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://127.0.0.1:3306/ds_1?characterEncoding=utf8&useSSL=false");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("ds_1", dataSource2);
        return dataSourceMap;
    }

}
