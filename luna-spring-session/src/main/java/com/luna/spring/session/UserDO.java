package com.luna.spring.session;

/**
 * @author luna@mac
 * 2021年04月13日 10:28
 */
public class UserDO {

    private String username;

    private String password;

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
            '}';
    }
}
