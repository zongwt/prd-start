package com.prd.client;

import com.prd.client.clientImpl.MenuFallback;
import com.prd.result.DataJsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="main-service", fallback= MenuFallback.class)
public interface MenuClient {

    @GetMapping("/getMenus")
    String getMenus(@RequestParam(value = "level") String level, @RequestParam(value = "pid", defaultValue = "1") String pid);
}
