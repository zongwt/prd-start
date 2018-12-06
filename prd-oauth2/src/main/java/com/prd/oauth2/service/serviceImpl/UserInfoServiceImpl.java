package com.prd.oauth2.service.serviceImpl;

import com.prd.oauth2.core.model.UserInfo;
import com.prd.oauth2.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserInfo findByUsername(String username){
        UserInfo userInfo = new UserInfo();
        SqlRowSet res = jdbcTemplate.queryForRowSet("SELECT * FROM sys_user where username = '" + username + "' ");
        while(res.next()){
            userInfo.setUsername(res.getString("USERNAME"));
            userInfo.setPassword(res.getString("PASSWORD"));
            userInfo.setId(res.getInt("ID"));
            return userInfo;
        }
        return userInfo;
    }
}
