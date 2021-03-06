package com.prd.client;

import com.prd.client.clientImpl.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name="main-service", fallback=UserFallback.class)
public interface UserFeignClient {

    @GetMapping("/listUsers")
    public String listUsers(@RequestParam(value = "username") String username);

}