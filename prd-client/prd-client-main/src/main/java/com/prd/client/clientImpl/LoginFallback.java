package com.prd.client.clientImpl;

import com.prd.client.LoginClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class LoginFallback implements LoginClient {

    @Override
    public String login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return "登陆服务调用失败";
    }
}
