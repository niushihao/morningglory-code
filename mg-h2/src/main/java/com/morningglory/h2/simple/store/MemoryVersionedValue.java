package com.morningglory.h2.simple.store;

import lombok.Data;

public class MemoryVersionedValue<V> {

    private V value;

    private long version;

    private boolean isTombstone;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isTombstone() {
        return isTombstone;
    }

    public void setTombstone(boolean tombstone) {
        isTombstone = tombstone;
    }
}
