package com.luna.spring.session.controller;

import com.luna.spring.session.config.LoginInterceptor;
import com.luna.spring.session.utils.CookieUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luna@mac
 * 2021年04月10日 10:20
 */
@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletResponse response, String username, String password) {
        if (username.equals("123") && password.equals("123")) {
            Cookie cookie = new Cookie(CookieUtils.SESSION_KEY_NAME, "luna-session");
            cookie.setPath("/");
            cookie.setMaxAge(60 * 3);
            response.addCookie(cookie);
            LoginInterceptor.sessionKey = "luna-session";
            return "success";
        }
        return "fail";
    }

    @GetMapping("/exit")
    public void exit() {
        LoginInterceptor.sessionKey = "";
    }

    @GetMapping("/main/index")
    public String main() {
        return "index";
    }

}
