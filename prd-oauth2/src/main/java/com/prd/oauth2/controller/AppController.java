package com.prd.oauth2.controller;

import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.core.model.UserInfo;
import com.prd.oauth2.core.result.ReturnT;
import com.prd.oauth2.service.UserInfoService;
import com.prd.oauth2.user.SsoUser;
import com.prd.oauth2.util.SsoLoginHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

/**
 * sso server (for app)
 *
 * @author zongwt
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(String username, String password, HttpServletResponse response) {

        if (StringUtils.isBlank(username)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (StringUtils.isBlank(password)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "Please input password.");
        }
        UserInfo existUser = userInfoService.findByUsername(username);
        if (existUser == null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "username is invalid.");
        }
        if (!existUser.getPassword().equals(password)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, "password is invalid.");
        }
        // login success
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(existUser.getId());
        ssoUser.setUsername(existUser.getUsername());
        String sessionId = UUID.randomUUID().toString();
        SsoLoginHelper.login(sessionId, ssoUser);
        response.setHeader("Access-Control-Expose-Headers", Conf.SSO_SESSIONID);
        response.setHeader(Conf.SSO_SESSIONID,sessionId);
        // result
        return new ReturnT<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {
        // logout
        SsoLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<SsoUser> logincheck(String sessionId) {
        // logout
        SsoUser ssoUser = SsoLoginHelper.loginCheck(sessionId);
        if (ssoUser == null) {
            return new ReturnT<SsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<SsoUser>(ssoUser);
    }
}