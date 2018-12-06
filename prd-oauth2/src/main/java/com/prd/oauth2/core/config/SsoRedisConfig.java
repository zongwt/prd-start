package com.prd.oauth2.core.config;

import com.prd.oauth2.util.JedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zongwt
 */
@Configuration
public class SsoRedisConfig implements InitializingBean {

    @Value("${redis.host}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisport;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisUtil.init(redisIp+":"+redisport);
    }

}
