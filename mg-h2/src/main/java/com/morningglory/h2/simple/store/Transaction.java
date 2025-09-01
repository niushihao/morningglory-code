package com.morningglory.h2.simple.store;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Transaction {


    // private ThreadLocal<AtomicLong> currentVersionHolder = ThreadLocal.withInitial(currentVersion);

    private long transactionId;

    public Transaction(long transactionId) {
        this.transactionId = transactionId;
    }

    public static Transaction begin() {
        return new Transaction(MemoryVersionedMap.currentVersion.incrementAndGet());
    }

    public static void commit() {

    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
