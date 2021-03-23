package com.morningglory.basic.antTask;

/**
 * @author qianniu
 * @date 2020/7/31 12:46 上午
 * @desc
 */
public class Task {

    private String key;

    private String value;

    public Task() {
    }

    public Task(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String execute() {
        String result = value;
        Cache.put("String key", "String result");
        return result;
    }
}
