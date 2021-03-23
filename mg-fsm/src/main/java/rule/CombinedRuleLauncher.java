package rule;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author qianniu
 * @date 2021/1/6 1:56 下午
 * @desc
 * @see :https://juejin.cn/post/6894075149784449031
 * 规则1：周一周五新客结算，结算金额为 price * 0.2
 *
 * 规则2: 年龄大于18岁、金额大于10的结算，结算金额为(price - 10) * 0.8
 *
 * 主要逻辑如下,先过滤掉不需要的订单，然后对剩下的订单进行结算
 */
public class CombinedRuleLauncher {

    public static void main(String[] args) {
        List<Order> orders = mockData();

        Map<String, Expression> expressionCache = new HashMap<String, Expression>();

        System.out.println("结算rule1");
        List<String> filterRule1 =
                Arrays.asList("orderDate.getDayOfWeek().getValue() == 1 || orderDate.getDayOfWeek().getValue() == 5", "isNew");
        String settleRule1 = "price * 0.2";
        settle(orders,filterRule1,settleRule1,expressionCache);


        System.out.println("结算rule2");
        List<String> filterRule2 =
                Arrays.asList("age > 18", "price > 10");
        String settleRule2 = "(price - 10) * 0.8";
        settle(orders,filterRule2,settleRule2,expressionCache);
    }



    public static void settle(List<Order> orders, List<String> filterRule,
                              String settleRule, Map<String, Expression> expressionCache) {
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        Stream<Order> stream = orders.stream();
        for (String rule : filterRule) {
            Expression expression = FunctionUtil
                    .cacheFunction(s -> expressionParser.parseExpression(s), rule, expressionCache);
            // 根据规则过滤不需要计算的订单
            stream = filter(stream, expression);
        }

        // 根据规则计算结算金额
        Expression expression = FunctionUtil
                .cacheFunction(s -> expressionParser.parseExpression(s), settleRule, expressionCache);
        stream.forEach(o -> System.out.println(o.getUserId() + expression.getValue(o)));
    }







    private static List<Order> mockData() {
        return new ArrayList<Order>(){{
            //年龄19，不是新客，周一下单，金额11
            add(new Order("张三",19,false, LocalDate.of(2020,11,9),new BigDecimal(11)));
            //年龄17，是新客，周五下单，金额19
            add(new Order("李四",17,true,LocalDate.of(2020,11,13),new BigDecimal(19)));
            //年龄17，不是新客，周六下单，金额9
            add(new Order("王五",17,true,LocalDate.of(2020,11,14),new BigDecimal(9)));
        }};
    }


    public static <T> Stream<T> filter(Stream<T> stream, Expression expression) {
        return stream.filter(s -> expression.getValue(s, Boolean.class));
    }


}
