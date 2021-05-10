package com.luna.spring.session;

import com.luna.redis.util.RedisHashUtil;
import com.luna.redis.util.RedisValueUtil;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author luna@mac
 * 2021年04月13日 14:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private RedisValueUtil redisValueUtil;

    @Test
    public void atest() {
        redisValueUtil.set("luna:message", "nihao");
        Object luna = redisValueUtil.get("luna");
        System.out.println(luna);
    }

    @Autowired
    private RedisHashUtil redisHashUtil;

    @Test
    public void btest() {
        redisHashUtil.set("String", Maps.newHashMap("123", "123"));
    }
}
