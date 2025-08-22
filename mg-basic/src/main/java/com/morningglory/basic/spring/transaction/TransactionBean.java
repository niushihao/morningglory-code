package com.morningglory.basic.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qianniu
 * @date 2020/6/24 10:55 上午
 * @desc
 */
@Component
public class TransactionBean {

    @Autowired
    TransactionBean transactionBean;

    @Transactional
    public String getMsg(){
        return "123";
    }

    public static void main(String[] args) {

        Boolean flag = null;
        if(flag){
            System.out.println("111");
        }
    }
}
