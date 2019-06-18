package com.prd.client.clientImpl;

import com.prd.client.UserFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 熔断器
 */
@Component
public class UserFallback implements UserFeignClient {

    @Override
    public String listUsers(@RequestParam(value = "username") String username) {
        return "main-service服务调用失败";
    }
}


