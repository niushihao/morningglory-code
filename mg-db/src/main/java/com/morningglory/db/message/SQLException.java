package com.morningglory.db.message;

import java.util.Iterator;

public class SQLException extends java.lang.Exception
        implements Iterable<Throwable> {
    @Override
    public Iterator<Throwable> iterator() {
        return null;
    }
}
