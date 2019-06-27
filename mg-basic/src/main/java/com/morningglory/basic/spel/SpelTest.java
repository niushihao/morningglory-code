package com.morningglory.basic.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @Author: qianniu
 * @Date: 2019-02-26 15:02
 * @Desc: spring spel表达式
 */
public class SpelTest {

    public static void main(String[] args) {

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);
    }
}
