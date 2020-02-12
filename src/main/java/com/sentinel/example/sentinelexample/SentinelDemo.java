package com.sentinel.example.sentinelexample;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class SentinelDemo {
    //定义资源
    private static String resource="function";
    public static void main(String[] args) {
        initFlowRules(); //初始化一个规则
        while(true){
            Entry entry=null;
            try{
                entry= SphU.entry(resource); //它做了什么
                System.out.println("Hello Word");
            }catch (BlockException e){//如果被限流了，那么会抛出这个异常
                System.out.println("我被限流了");
                e.printStackTrace();
            }finally {
                if(entry!=null){
                    entry.exit();// 释放
                }
            }
        }
    }
    //定义规则
    private static void initFlowRules(){
        List<FlowRule> rules=new ArrayList<>(); //限流规则的集合
        FlowRule flowRule=new FlowRule();
        flowRule.setResource(resource);//资源(方法名称、接口）
        // 基于线程限流 RuleConstant.FLOW_GRADE_THREAD
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS); //基于QPS限流
        flowRule.setCount(10);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }
}