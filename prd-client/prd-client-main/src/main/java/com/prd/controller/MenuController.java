package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.MenuClient;
import com.prd.result.DataJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @Autowired
    private MenuClient menuClient;

    @GetMapping("/getMenus")
    @HystrixCommand(fallbackMethod="MenuFallback")
    public String getMenus(@RequestParam(value = "level") String level, @RequestParam(value = "pid", defaultValue = "1") String pid){
        return menuClient.getMenus(level, pid);
    }

    /**
     * 登陆服务降级
     * @return
     */
    public String MenuFallback(@RequestParam(value = "level") String level, @RequestParam(value = "pid", defaultValue = "1") String pid){
        DataJsonResult dataJsonResult = new DataJsonResult();
        dataJsonResult.setCode("500");
        dataJsonResult.setMsg("菜单获取异常！");
        return "菜单获取异常！";
    }
}
