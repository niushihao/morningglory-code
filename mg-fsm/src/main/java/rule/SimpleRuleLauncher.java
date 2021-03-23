package rule;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;

/**
 * @author qianniu
 * @date 2021/1/6 1:53 下午
 * @desc 一个简单的规则
 * 计算订单的结算,假设所有订单的结算金额都为总价的80%
 */
public class SimpleRuleLauncher {

    public static void main(String[] args) {

        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("price * 0.8");
        Order order = new Order();
        order.setPrice(BigDecimal.TEN);
        BigDecimal value = expression.getValue(order, BigDecimal.class);
        System.out.println(value);
    }
}
