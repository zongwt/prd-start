package com.prd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.prd.client.MenuClient;
import com.prd.result.DataJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class MenuController {

    @Autowired
    private MenuClient menuClient;

    @ApiOperation(value="菜单获取", notes="菜单获取")
    @RequestMapping("/getMenus")
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
