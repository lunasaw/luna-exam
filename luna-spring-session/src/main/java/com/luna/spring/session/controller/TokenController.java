package com.luna.spring.session.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.luna.spring.session.anno.NotRepeatSubmit;
import com.luna.spring.session.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private static final Logger log = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private RedisTemplate       redisTemplate;

    /**
     * API Token
     *
     * @param sign
     * @return
     */
    @PostMapping("/accessToken")
    public ApiResponse<AccessToken> apiToken(@RequestHeader("timestamp") String timestamp,
        @RequestHeader("sign") String sign, @RequestBody AppInfo appInfo) {
        System.out.println(JSON.toJSONString(appInfo));
        Assert.isTrue(
            !StringUtils.isEmpty(appInfo.getAppId()) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign),
            "参数错误");

        long reqeustInterval = System.currentTimeMillis() - Long.parseLong(timestamp);
        Assert.isTrue(reqeustInterval < 5 * 60 * 1000, "请求过期，请重新请求");

        // 1. 根据appId查询数据库获取appSecret
        // AppInfo appInfo = new AppInfo("1", "12345678954556");

        // 2. 校验签名
        String signString = timestamp + appInfo.getAppId() + appInfo.getKey();
        System.out.println(signString);
        String signature = MD5Util.encode(signString);
        log.info(signature);
        Assert.isTrue(signature.equals(sign), "签名错误");

        // 3. 如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = this.saveToken(0, appInfo, null);

        return ApiResponse.success(accessToken);
    }

    @NotRepeatSubmit(value = 5000)
    @PostMapping("user_token")
    public ApiResponse<UserInfo> userToken(String username, String password) {
        // 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        UserInfo userInfo = new UserInfo(username, "81255cb0dca1a5f304328a70ac85dcbd", "111111");
        String pwd = password + userInfo.getSalt();
        String passwordMD5 = MD5Util.encode(pwd);
        Assert.isTrue(passwordMD5.equals(userInfo.getPassword()), "密码错误");

        // 2. 保存Token
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    private AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();

        // token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();

        // 4. 保存token
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);

        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }

        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);

        AccessToken accessToken = new AccessToken(token, expireTime);

        return accessToken;
    }

    public static void main(String[] args) {
        long timeMillis = System.currentTimeMillis();
        long timestamp = 1;
        System.out.println(timeMillis);
        System.out.println(timeMillis);
        String signString = timeMillis + "1173282254" + "luna";
        System.out.println(signString);
        String sign = MD5Util.encode(signString);
        System.out.println(MD5Util.encode("16206109430661173282254luna"));
        System.out.println(sign);

        System.out.println("-------------------");
        signString = "password=123456&username=1&12345678954556"
            + "ff03e64b-427b-45a7-b78b-47d9e8597d3b1529815393153sdfsdfsfs" + timestamp + "A1scr6";
        sign = MD5Util.encode(signString);
        System.out.println(sign);
    }
}