package com.sentinel.example.sentinelexample;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SentinelExampleApplication {

    public static void main(String[] args) {
        initFlowRules(); //限流demo
        initDegradeRule(); //熔断demo
        SpringApplication.run(SentinelExampleApplication.class, args);
    }

    //定义限流规则
    private static void initFlowRules(){
        List<FlowRule> rules=new ArrayList<>(); //限流规则的集合
        FlowRule flowRule=new FlowRule();
        flowRule.setResource("sendMsg");//资源(方法名称、接口）
        // 基于线程限流 RuleConstant.FLOW_GRADE_THREAD
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS); //基于QPS限流
        flowRule.setCount(20);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }

    //定义熔断规则
    public static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("getUser");
        // 80s内调用接口出现异常次数超过5的时候, 进行熔断
        rule.setCount(5);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(80);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
