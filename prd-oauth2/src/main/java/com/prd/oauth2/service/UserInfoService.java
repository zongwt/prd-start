package com.prd.oauth2.service;

import com.prd.oauth2.core.model.UserInfo;

public interface UserInfoService {

    public UserInfo findByUsername(String username);

}
