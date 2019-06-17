package com.prd.service;

import com.prd.config.RedisService;
import com.prd.result.DataJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@Api
public class LoginService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value="登陆", notes="登陆")
    @RequestMapping("/login")
    public DataJsonResult login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password ) throws SQLException {
        System.out.println("username:" + username + "; password:" + password);
        List<Map<String, Object>> res = jdbcTemplate.queryForList("SELECT * FROM sys_user where username = '" + username +"' and password = '"+password+"'");
        redisService.add("userinfo2", res);
        DataJsonResult dataJsonResult = new DataJsonResult();
        if(res.size() <= 0){
            dataJsonResult.setCode("300");
        } else {
            dataJsonResult.setCode("200");
        }
        dataJsonResult.setData(res);
        return dataJsonResult;
    }
}
