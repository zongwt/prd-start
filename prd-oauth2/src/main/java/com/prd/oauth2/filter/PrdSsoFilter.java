package com.prd.oauth2.filter;

import com.prd.oauth2.conf.Conf;
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
 * web sso filter
 *
 * @author zongwt 2018-04-03
 */
public class PrdSsoFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(PrdSsoFilter.class);

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
        String servletPath = ((HttpServletRequest) request).getServletPath();
        String link = req.getRequestURL().toString()+ "?" + (req.getQueryString());
        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {

            // remove cookie
            SsoLoginHelper.removeSessionIdInCookie(req, res);

            // redirect logout
            String logoutPageUrl = ssoServer.concat(Conf.SSO_LOGOUT);
            res.sendRedirect(logoutPageUrl);
            return;
        }
        // login filter
        SsoUser ssoUser = null;

        // valid cookie user
        String cookieSessionId = SsoLoginHelper.getSessionIdByCookie(req);
        ssoUser = SsoLoginHelper.loginCheck(cookieSessionId);

        // valid param user, client login
        if (ssoUser == null) {

            // remove old cookie
            SsoLoginHelper.removeSessionIdInCookie(req, res);

            // set new cookie
            String paramSessionId = request.getParameter(Conf.SSO_SESSIONID);
            if (paramSessionId != null) {
                ssoUser = SsoLoginHelper.loginCheck(paramSessionId);
                if (ssoUser != null) {
                    SsoLoginHelper.setSessionIdInCookie(res, paramSessionId);
                }
            }
        }
        // valid login fail
        if (ssoUser == null) {
            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {
                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println(JacksonUtil.writeValueAsString(Conf.SSO_LOGIN_FAIL_RESULT));
                return;
            } else {
                // redirect logout
                String loginPageUrl = ssoServer.concat(Conf.SSO_LOGIN)
                        + "?" + Conf.REDIRECT_URL + "=" + link.replaceAll("&", "%26");
                res.sendRedirect(loginPageUrl);
                return;
            }
        }
        // ser sso user
        request.setAttribute(Conf.SSO_USER, ssoUser);
        // already login, allow
        chain.doFilter(request, response);
        return;
    }

}
