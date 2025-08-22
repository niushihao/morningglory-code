package com.morningglory.basic.spring.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author qianniu
 * @date 2022/12/9
 * @desc
 */
//@Component
@Slf4j
public class TransactionHookTest {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private BizServiceA bizServiceA;

    @Transactional
    public void test(){

        // do business
        bizServiceA.invokeAHasTransactional();

        // TODO which mode should i choose
        // mode one
        if(TransactionSynchronizationManager.isSynchronizationActive()){
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
                @Override
                public void afterCommit() {
                    // TODO mqSendMessage
                }
            });
        }

        // mode two
        if(TransactionSynchronizationManager.isActualTransactionActive()){
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
                @Override
                public void afterCommit() {
                    // TODO mqSendMessage
                }
            });
        }
    }

    @TransactionalEventListener
    public void afterCommit(){

        //applicationEventPublisher.publishEvent();
    }
}
