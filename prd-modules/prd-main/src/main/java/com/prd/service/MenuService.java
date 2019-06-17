package com.prd.service;

import com.prd.config.RedisService;
import com.prd.tools.Utils;
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
public class MenuService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value="菜单", notes="菜单")
    @RequestMapping("/getMenus")
    public String getMenus(@RequestParam(value = "level") String level,
                                   @RequestParam(value = "pid", defaultValue = "1") String pid) throws SQLException {
        List<Map<String, Object>> res = jdbcTemplate.queryForList("SELECT * FROM sys_menu where LEVEL = '" + level +"' AND PID = '"+pid+"' ");
        return Utils.coverToJson(res).toString();
    }
}
