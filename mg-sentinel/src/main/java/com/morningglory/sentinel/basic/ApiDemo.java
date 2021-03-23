package com.morningglory.sentinel.basic;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.statistic.MetricEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/10/2 4:40 下午
 * @desc Sentinel API 测试
 */
@Slf4j
public class ApiDemo {

    public static void main(String[] args) {

        System.out.println(MetricEvent.EXCEPTION.ordinal());
        // 配置规则.
        initFlowRules();

        for(int i= 0; i< 15; i++){
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            Entry temp = null;
            try (Entry entry = SphU.entry("HelloWorld")) {
                temp= entry;
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
                log.error("blocked,entry = {},ex = {}",temp,ex.getMessage(),ex);
            }
        }

    }


    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(0);
        // Set limit QPS to 20.
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @SentinelResource("HelloWorld")
    public void helloWorld() {
        // 资源中的逻辑
        System.out.println("hello world");
    }
}
