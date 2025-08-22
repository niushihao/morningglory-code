package com.morningglory.basic.spel;

import com.morningglory.basic.pojo.User;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @Author: qianniu
 * @Date: 2019-02-26 15:02
 * @Desc: spring spel表达式
 */
public class SpelTest {

    public static void main(String[] args) {

        User user = new User();
        user.setId(1);
        user.setName("TEST");
        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext evaluationContext = new StandardEvaluationContext();
        //evaluationContext.setVariable("basePojo", null);
        evaluationContext.setVariable("user", user);

        String name = "#user.name == 'TEST' or (#basePojo.appKey == null)";
        Expression expression = parser.parseExpression(name);
        Boolean value = expression.getValue(evaluationContext, Boolean.class);
        System.out.println(value);


        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);
    }
}
