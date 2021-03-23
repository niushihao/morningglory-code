package com.morningglory.basic.oom;

/**
 * @author qianniu
 * @date 2020/12/29 12:19 上午
 * @desc
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/nsh/oom/stackofedump.hprof
 */
public class StackOFE {

    public static void stackOverFlowErrorMethod() {
        stackOverFlowErrorMethod();
    }

    public static void main(String[] args) {
        stackOverFlowErrorMethod();
    }
}
