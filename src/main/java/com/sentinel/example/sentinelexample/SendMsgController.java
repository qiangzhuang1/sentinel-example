package com.sentinel.example.sentinelexample;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流demo
 */
@RestController
public class SendMsgController {

    @SentinelResource(value = "sendMsg",blockHandler="exceptionHandler") //针对方法级别的限流
    @GetMapping("/sendMsg")
    public String sayHello(){
        System.out.println("发送短信成功");
        return "发送短信成功";
    }

    public String exceptionHandler(BlockException e) {
        e.printStackTrace();
        return "发送短信被限流";
    }
}
