package com.luna.springdemo;

import com.luna.redis.util.RedisBoundUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.luna.springdemo.redis
 * @ClassName: RedisTest
 * @Author: luna
 * @CreateTime: 2020/8/23 17:07
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisBoundUtil redisBoundUtil;

    @Test
    public void atest() {
        redisBoundUtil.set("luna", "luna,redis");
    }
}
