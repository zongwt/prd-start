package com.prd.filter;

import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.filter.PrdSsoTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrdOauth2Config {

    @Value("${prd.sso.server}")
    private String ssoServer;

    @Value("${prd.sso.logout}")
    private String ssoLogout;

    @Bean
    public FilterRegistrationBean prdSsoFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("PrdSsoTokenFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new PrdSsoTokenFilter());
        registration.addInitParameter(Conf.SSO_SERVER, ssoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, ssoLogout);
        return registration;
    }

}
