package com.morningglory.basic.spring.transaction;

import com.morningglory.basic.spring.autoconfig.EnableLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author qianniu
 * @date 2020/6/24 11:09 上午
 * @desc
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class TransactionConfig {

    @Bean
    public PlatformTransactionManager txManager() {
        return new AbstractPlatformTransactionManager() {
            @Override
            protected Object doGetTransaction() throws TransactionException {
                return null;
            }

            @Override
            protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {

            }

            @Override
            protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {

            }

            @Override
            protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {

            }
        };
    }
}
