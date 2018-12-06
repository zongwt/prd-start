package com.prd.client;


import com.prd.client.clientImpl.LoginFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="main-service", fallback= LoginFallback.class)
public interface LoginClient {

    @GetMapping("/login")
    public String login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);
}
