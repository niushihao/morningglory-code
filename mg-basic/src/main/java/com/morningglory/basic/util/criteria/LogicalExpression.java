package com.morningglory.basic.util.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qianniu
 * @date 2024/4/3
 * @desc 逻辑连接条件（AND或OR）
 */
public class LogicalExpression {

    // 可以是Criterion或LogicalExpression
    private List<Object> parts;
    private String operator;


    public LogicalExpression(String operator) {
        this.parts = new ArrayList<>();
        this.operator = operator;
    }

    public void add(Object part) {
        parts.add(part);
    }

    @Override
    public String toString() {
        return "(" + parts.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" " + operator + " "))
                + ")";
    }


}
