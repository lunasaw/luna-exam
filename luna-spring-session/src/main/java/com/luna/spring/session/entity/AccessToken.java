package com.luna.spring.session.entity;

import java.util.Date;

public class AccessToken {
    /** token */
    private String token;

    /** 失效时间 */
    private Date   expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public AccessToken() {}

    public AccessToken(String token, Date expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }
}