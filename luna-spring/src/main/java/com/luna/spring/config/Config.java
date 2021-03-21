package com.luna.spring.config;

import com.luna.baidu.config.BaiduConfigValue;
import com.luna.baidu.config.GetBaiduKey;
import com.luna.common.spring.SpringUtils;
import com.luna.redis.util.RedisBoundUtil;
import com.luna.redis.util.RedisKeyUtil;
import com.luna.tencent.config.TencentConfigValue;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Package: com.luna.spring.config
 * @ClassName: Config
 * @Author: luna
 * @CreateTime: 2020/8/6 21:23
 * @Description:
 */
@SpringBootConfiguration
public class Config {

    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

    @Bean
    public TencentConfigValue tencentConfigValue() {
        return new TencentConfigValue();
    }

    @Bean
    public BaiduConfigValue baiduConfigValue() {
        return new BaiduConfigValue();
    }

    @Bean
    public GetBaiduKey getBaiduKey() {
        return new GetBaiduKey();
    }

    @Bean
    public RedisKeyUtil redisUtil() {
        return new RedisKeyUtil();
    }

    @Bean
    public RedisBoundUtil redisBoundUtil() {
        return new RedisBoundUtil();
    }
}
