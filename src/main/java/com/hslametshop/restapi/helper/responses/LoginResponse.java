package com.hslametshop.restapi.helper.responses;

public class LoginResponse {
    private String token;
    private String refreshToken;
    private long expiration;

    public LoginResponse() {
    }

    public LoginResponse(String token, String refreshToken, long expiration) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

}
