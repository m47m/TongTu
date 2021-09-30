package com.example.tongtu.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Token_save extends LitePalSupport {
    @Column(unique = true)
    private String token;

    @Column(unique = true)
    private String securityToken;

    @Column(unique = true)
    private String accessKeySecret;

    @Column(unique = true)
    private String accessKeyId;

    @Column(unique = true)
    private String expiration;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
