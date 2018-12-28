package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.LoginClient;
import com.prd.result.DataJsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginClient loginClient;

    @ApiOperation(value="登陆", notes="登陆")
    @GetMapping("/login")
    @HystrixCommand(fallbackMethod="loginFallback")
    public DataJsonResult ListUsers(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        DataJsonResult dataJsonResult = new DataJsonResult();
        String users = this.loginClient.login(username, password);
        String token = "";
        dataJsonResult.setCode("200");
        return dataJsonResult;
    }

    /**
     * 登陆服务降级
     * @return
     */
    public DataJsonResult loginFallback(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        DataJsonResult dataJsonResult = new DataJsonResult();
        dataJsonResult.setCode("500");
        dataJsonResult.setMsg("登陆服务异常！");
        return dataJsonResult;
    }
}
