package com.prd.client.clientImpl;

import com.prd.client.UserFeignClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 熔断器
 */
@Component
public class UserFallback implements UserFeignClient {

    @Override
    public String listUsers(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return "main-service服务调用失败";
    }
}


