package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Api
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${server.port}")
    String port;

    @ApiOperation(value="用户信息获取", notes="用户信息获取")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "string")
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