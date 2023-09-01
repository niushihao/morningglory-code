package com.morningglory.basic.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author qianniu
 * @date 2020/6/24 11:09 上午
 * @desc
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class TransactionConfig {


    /**
     * spring:
     *   datasource:
     *     url: jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&useSSL=false
     *     ##url: jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8
     *     driverClassName: com.mysql.jdbc.Driver
     *     username: root
     *     password: 123456
     *  如果配了 spring.datasource则自动装配
     *  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
     * @return
     */
    @Bean
    public DataSource dataSource(){

        // todo 尝试下binder 的用法
        //new RelaxedDataBinder(pickCompensationConfigInstance).bind(new MutablePropertyValues(properties));
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&useSSL=false");
        properties.setDriverClassName("com.mysql.jdbc.Driver");
        properties.setUsername("root");
        properties.setPassword("123456");

        return properties.initializeDataSourceBuilder().build();

    }

    /**
     * 平时用springboot时没有手动创建过这个bean，是因为在
     * org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
     * 自动注入了
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager dataSourceTransactionManager) {
        return new TransactionTemplate(dataSourceTransactionManager);
    }
}
