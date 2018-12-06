package com.prd.client.clientImpl;

import com.prd.client.MenuClient;
import com.prd.result.DataJsonResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class MenuFallback implements MenuClient {

    @Override
    public String getMenus(@RequestParam(value = "level") String level, @RequestParam(value = "pid", defaultValue = "1") String pid) {
        DataJsonResult dataJsonResult = new DataJsonResult();
        dataJsonResult.setCode("500");
        dataJsonResult.setMsg("服务调用失败");
        return "服务调用失败";
    }
}
