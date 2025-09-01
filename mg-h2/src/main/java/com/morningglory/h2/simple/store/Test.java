package com.morningglory.h2.simple.store;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    public static void main(String[] args) {

        MemoryVersionedMap<String,String> store = new MemoryVersionedMap();


        // 初始化
        for (int i =1; i< 11; i++) {
            Transaction transaction = new Transaction(i);
            store.put(transaction,"1",String.valueOf(i));
        }

        //
        Transaction begin = Transaction.begin();
        begin.setTransactionId(2);
        String s = store.get(begin, "1");
        //log.info("s:{}",s);
        System.out.println(s);
    }
}
