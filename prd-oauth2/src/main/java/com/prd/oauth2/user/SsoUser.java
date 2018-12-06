package com.prd.oauth2.user;

import java.io.Serializable;

/**
 * @author zongwt
 */
public class SsoUser implements Serializable {
    private static final long serialVersionUID = 42L;

    private int userid;

    private String username;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
