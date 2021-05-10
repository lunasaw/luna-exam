package com.luna.spring.session.entity;

/**
 * @author luna@mac
 * 2021年04月13日 10:28
 */
public class UserDO {

    private String username;

    private String password;

    private String      salt;

    private AccessToken accessToken;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UserDO{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            ", accessToken=" + accessToken +
            '}';
    }
}
