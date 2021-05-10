package com.luna.spring.session.controller;

import com.luna.common.anno.ResponseResult;
import com.luna.common.text.RandomStrUtil;

import com.luna.redis.util.RedisKeyUtil;
import com.luna.redis.util.RedisValueUtil;
import com.luna.spring.session.entity.UserDO;
import com.luna.spring.session.config.LoginInterceptor;
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
@ResponseResult
public class LoginController {

    @Autowired
    private RedisValueUtil redisValueUtil;

    @Autowired
    private RedisKeyUtil   redisKeyUtil;

    @GetMapping("/login")
    public UserDO login(HttpServletResponse response, UserDO userDO) {
        String s = RandomStrUtil.generateNonceStr();
        if (userDO.getUsername().equals("123") && userDO.getPassword().equals("123")) {
            // session 存用户信息 和sessionKey
            // Cookie 存sessionKey
            redisValueUtil.set(LoginInterceptor.sessionKey + s, userDO, 7, TimeUnit.DAYS);
            Cookie cookie = new Cookie(CookieUtils.SESSION_KEY_NAME, s);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 3);
            response.addCookie(cookie);
            return userDO;
        }
        throw new RuntimeException("没有该用户");
    }

    @GetMapping("/exit")
    public void exit(HttpServletRequest request) {
        String key = CookieUtils.getSessionKeyFromRequest(request);
        redisKeyUtil.delete(LoginInterceptor.sessionKey + key);
    }


    @GetMapping("/main/index")
    public String main() {
        return "index";
    }

}
