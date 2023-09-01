package com.morningglory.basic.spring.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author qianniu
 * @date 2022/11/29
 * @desc 传播属性测试
 */
@Component
public class PropagationTestService {

    @Resource
    private BizServiceA bizServiceA;

    @Transactional
    public void useRequiredAndAllHasTransactional(){
        bizServiceA.invokeAllHasTransactional();
    }

    @Transactional
    public void useRequiredAndSubHasTransactional(){
        bizServiceA.invokeAHasTransactional();
    }
}
