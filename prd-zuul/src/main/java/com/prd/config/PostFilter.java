package com.prd.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.user.SsoUser;
import com.prd.oauth2.util.SsoLoginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class PostFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取请求的token
        HttpServletRequest req = ctx.getRequest();
        String link = req.getRequestURL().toString();
        String sessionid = SsoLoginHelper.cookieSessionIdGetByHeader(req);
        SsoUser ssoUser = SsoLoginHelper.loginCheck(sessionid);
        // 生成新的toekn并放入头中返回
        String sessionId = UUID.randomUUID().toString();
        SsoLoginHelper.login(sessionId, ssoUser);
        HttpServletResponse resp = ctx.getResponse();
        // 浏览器默认不可获取除content-type其他的的参数，必须要增加下面代码
        resp.setHeader("Access-Control-Expose-Headers", Conf.SSO_SESSIONID);
        resp.setHeader(Conf.SSO_SESSIONID,sessionId);
        return null;
    }

}
