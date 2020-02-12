package com.sentinel.example.sentinelexample;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 熔断降级 demo
 */
@RestController
public class DegradeController {

    @SentinelResource(value = "getUser",blockHandler="exceptionHandler") //针对方法级别的限流
    @GetMapping("/getUser")
    public String sayHello(@RequestParam("userId") String userId){
        // 模拟调用服务出现异常
        if ("0".equals(userId)) {
            throw new RuntimeException();
        }
        System.out.println("获取用户信息成功");
        return "获取用户信息成功";
    }

    public String exceptionHandler(String userId,BlockException e) {
        return "getUser error, exceptionHandler res: " + userId;
    }
}
