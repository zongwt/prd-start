package com.prd.service;

import com.prd.tools.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@Api
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value="用户信息", notes="用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "string")
    @RequestMapping("/listUsers")
    public String getMenus(HttpServletRequest request) throws SQLException {
        String name = request.getParameter("username");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM sys_user WHERE 1 = 1");
        if(Utils.availableStr(name)){
            sql.append(" AND USERNAME like '%" + name +"%'");
        }
        List<Map<String, Object>> res = jdbcTemplate.queryForList(sql.toString());
        res.toString();
        return Utils.coverToJson(res).toString();
    }
}

