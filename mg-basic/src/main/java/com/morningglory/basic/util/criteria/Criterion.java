package com.morningglory.basic.util.criteria;

/**
 * @author qianniu
 * @date 2024/4/3
 * @desc  基本条件单元，例如 "a = 1"
 */
public class Criterion {

    private String field;
    private String operator;
    private Object value;

    public Criterion(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public String toString() {
        // 这里为了演示方便，直接转换为字符串表示
        // 在实际用途中，可能需要生成特定的查询语言或者操作符
        return String.format("%s %s %s", field, operator, value);
    }
}
