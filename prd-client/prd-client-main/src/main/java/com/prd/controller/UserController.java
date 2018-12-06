package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${server.port}")
    String port;

    @GetMapping("/listUsers")
    @HystrixCommand(fallbackMethod="listUsersByRibbonFallback")
    public String ListUsers(HttpServletRequest request){
        String users = this.userFeignClient.listUsers(request);
        return users;
    }

    /**
     * 服务降级
     * @return
     */
    public String listUsersByRibbonFallback(HttpServletRequest request){
        return "listUsersByRibbon异常，端口：" + port;
    }

}