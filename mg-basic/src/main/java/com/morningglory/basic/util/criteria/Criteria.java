package com.morningglory.basic.util.criteria;

/**
 * @author qianniu
 * @date 2024/4/3
 * @desc
 */
public class Criteria {

    private Object expression;

    public void addEqual(String field, Object value) {
        add(new Criterion(field, "=", value));
    }

    public void addNotEqual(String field, Object value) {
        add(new Criterion(field, "<>", value));
    }

    private void add(Object exp) {
        if (expression == null) {
            expression = exp;
        } else if (expression instanceof Criterion) {
            // Create AND expression if there is already a single criterion
            LogicalExpression andExp = new LogicalExpression("AND");
            andExp.add(expression);
            andExp.add(exp);
            expression = andExp;
        } else if (expression instanceof LogicalExpression) {
            // Add to existing logical expression
            ((LogicalExpression) expression).add(exp);
        }
    }

    public void addExpression(String operator, Object... expressions) {
        LogicalExpression logicalExpression = new LogicalExpression(operator);
        for (Object exp : expressions) {
            logicalExpression.add(exp);
        }

        if (expression == null) {
            expression = logicalExpression;
        } else {
            LogicalExpression current = (LogicalExpression) expression;
            current.add(logicalExpression);
        }
    }

    // 用于生成最终的查询语句或者条件字符串
    public String build() {
        if (expression != null) {
            return expression.toString();
        }
        return "";
    }

    public static void main(String[] args) {
        Criteria criteria = new Criteria();
        criteria.addEqual("a", 1);
        criteria.addEqual("b", 2);

        // 创建子条件
        Criteria subCriteria = new Criteria();
        subCriteria.addEqual("c", 4);
        subCriteria.addEqual("d", 5);

        // 将子条件合并成逻辑 OR 表达式
        criteria.addExpression("OR", subCriteria.build());

        System.out.println(criteria.build());
    }

}
