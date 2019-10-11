package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.UserFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@Api
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Value("${server.port}")
    String port;

    @ApiOperation(value="用户信息获取", notes="用户信息获取")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "string")
    @RequestMapping("/listUsers")
    @HystrixCommand(fallbackMethod="listUsersByRibbonFallback")
    @ResponseBody
    public String ListUsers(@RequestParam(value = "username") String username){
        String users = this.userFeignClient.listUsers(username);
        return users;
    }

    /**
     * 服务降级
     * @return
     */
    public String listUsersByRibbonFallback(@RequestParam(value = "username") String username){
        return "listUsersByRibbon异常，端口：" + port;
    }

}