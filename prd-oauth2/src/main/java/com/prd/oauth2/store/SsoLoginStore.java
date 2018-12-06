package com.prd.oauth2.store;

import com.prd.oauth2.conf.Conf;
import com.prd.oauth2.user.SsoUser;
import com.prd.oauth2.util.JedisUtil;

/**
 * local login store
 *
 * @author zongwt 2018-04-02 20:03:11
 */
public class SsoLoginStore {

    /**
     * get
     *
     * @param sessionId
     * @return
     */
    public static SsoUser get(String sessionId) {

        String redisKey = redisKey(sessionId);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            SsoUser ssoUser = (SsoUser) objectValue;
            return ssoUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param sessionId
     */
    public static void remove(String sessionId) {
        String redisKey = redisKey(sessionId);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param sessionId
     * @param ssoUser
     */
    public static void put(String sessionId, SsoUser ssoUser) {
        String redisKey = redisKey(sessionId);
        JedisUtil.setObjectValue(redisKey, ssoUser);
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
