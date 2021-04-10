package com.luna.spring.session.controller;

import com.luna.common.text.RandomStrUtil;

import com.luna.redis.util.RedisBoundUtil;
import com.luna.redis.util.RedisKeyUtil;
import com.luna.redis.util.RedisValueUtil;
import com.luna.spring.session.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author luna@mac
 * 2021年04月10日 10:20
 */
@RestController
public class LoginController {

    @Autowired
    private RedisValueUtil redisValueUtil;

    @Autowired
    private RedisKeyUtil   redisKeyUtil;

    @GetMapping("/login")
    public String login(HttpServletResponse response, String username, String password) {
        String s = RandomStrUtil.generateNonceStr();
        if (username.equals("123") && password.equals("123")) {
            String key = RedisKeyUtil.getKey("luna-seession", s);
            redisValueUtil.set(s, "123", 7, TimeUnit.DAYS);
            Cookie cookie = new Cookie(CookieUtils.SESSION_KEY_NAME, s);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 3);
            response.addCookie(cookie);
            return "success";
        }
        return "fail";
    }

    @GetMapping("/exit")
    public void exit(HttpServletRequest request) {

        redisKeyUtil.delete(CookieUtils.getSessionKeyFromRequest(request));
    }

    @GetMapping("/main/index")
    public String main() {
        return "index";
    }

}
