package com.prd.oauth2.controller;

import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.core.model.UserInfo;
import com.prd.oauth2.exception.PrdSsoException;
import com.prd.oauth2.service.UserInfoService;
import com.prd.oauth2.user.SsoUser;
import com.prd.oauth2.util.SsoLoginHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * sso server (for web)
 *
 * @author zongwt
 */
@Controller
public class IndexController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        // login check
        SsoUser ssoUser = SsoLoginHelper.loginCheck(request);
        if (ssoUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("ssoUser", ssoUser);
            return "index";
        }
    }

    /**
     * Login page
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(Conf.SSO_LOGIN)
    public String login(Model model, HttpServletRequest request) {

        // login check
        SsoUser ssoUser = SsoLoginHelper.loginCheck(request);

        if (ssoUser != null) {

            // success redirect
            String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
            if (StringUtils.isNotBlank(redirectUrl)) {

                String sessionId = SsoLoginHelper.getSessionIdByCookie(request);
                String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;;

                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/";
            }
        }

        model.addAttribute("errorMsg", request.getParameter("errorMsg"));
        model.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
        return "login";
    }

    /**
     * Login
     *
     * @param request
     * @param redirectAttributes
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,
                          String username, String password) {

        String errorMsg = null;
        // valid
        UserInfo existUser = null;
        try {
            if (StringUtils.isBlank(username)) {
                throw new PrdSsoException("请输入用户名");
            }
            if (StringUtils.isBlank(password)) {
                throw new PrdSsoException("请输入密码");
            }
            existUser = userInfoService.findByUsername(username);
            if (existUser == null) {
                throw new PrdSsoException("用户名不存在");
            }
            if (existUser == null || !password.equals(existUser.getPassword())) {
                throw new PrdSsoException("密码不正确");
            }
        } catch (PrdSsoException e) {
            errorMsg = e.getMessage();
        }

        if (StringUtils.isNotBlank(errorMsg)) {
            redirectAttributes.addAttribute("errorMsg", errorMsg);

            redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
            return "redirect:/login";
        }

        // login success
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(existUser.getId());
        ssoUser.setUsername(existUser.getUsername());

        String sessionId = UUID.randomUUID().toString();

        SsoLoginHelper.login(response, sessionId, ssoUser);

        // success redirect
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        if (StringUtils.isNotBlank(redirectUrl)) {
            String redirectUrlFinal = "";
            if(redirectUrl.indexOf("?") > 0){
                redirectUrlFinal = redirectUrl + "&" + Conf.SSO_SESSIONID + "=" + sessionId;
            } else {
                redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;
            }

            return "redirect:" + redirectUrlFinal;
        } else {
            return "redirect:/";
        }
    }

    /**
     * Logout
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(Conf.SSO_LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        // logout
        SsoLoginHelper.logout(request, response);

        redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
        return "redirect:/login";
    }


}