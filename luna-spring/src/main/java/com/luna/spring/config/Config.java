package com.luna.spring.config;

import com.luna.redis.util.RedisKeyUtil;
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
    public RedisKeyUtil redisUtil() {
        return new RedisKeyUtil();
    }

}
