package com.prd.oauth2.filter;

import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.core.result.ReturnT;
import com.prd.oauth2.user.SsoUser;
import com.prd.oauth2.util.JacksonUtil;
import com.prd.oauth2.util.SsoLoginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * app sso filter
 *
 * @author zongwt
 */
public class PrdSsoTokenFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(PrdSsoTokenFilter.class);

    private String ssoServer;
    private String logoutPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ssoServer = filterConfig.getInitParameter(Conf.SSO_SERVER);
        if (ssoServer!=null && ssoServer.trim().length()>0) {
            logoutPath = filterConfig.getInitParameter(Conf.SSO_LOGOUT_PATH);
        }

        logger.info("PrdSsoFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 跨域设置
        res.setHeader("Access-Control-Allow-Origin",req.getHeader("origin"));
        res.setHeader("Access-Control-Allow-Origin","*");
        res.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        res.setHeader("Access-Control-Max-Age","3600");
        res.setHeader("Access-Control-Allow-Headers","x-requested-with,Cache-Control,Pragma,Content-Type,sessionid");
        res.setHeader("Access-Control-Allow-Credentials","true");
        String servletPath = ((HttpServletRequest) request).getServletPath();
        String link = req.getRequestURL().toString();

        String sessionid = SsoLoginHelper.cookieSessionIdGetByHeader(req);
        SsoUser ssoUser = SsoLoginHelper.loginCheck(sessionid);

        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {
            if (ssoUser != null) {
                SsoLoginHelper.logout(sessionid);
            }
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(new ReturnT(ReturnT.SUCCESS_CODE, null)));
            return;
        }
        // login filter
        if (ssoUser == null) {
            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(Conf.SSO_LOGIN_FAIL_RESULT));
            return;
        }
        // ser sso user
        request.setAttribute(Conf.SSO_USER, ssoUser);
        // already login, allow
        chain.doFilter(request, response);
        return;
    }
}
