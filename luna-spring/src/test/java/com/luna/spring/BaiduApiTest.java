package com.luna.spring;

import com.luna.baidu.api.BaiduApiConstant;
import com.luna.baidu.config.BaiduKeyGenerate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Luna@win10
 * @date 2020/5/6 12:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaiduApiTest {
    @Autowired
    private BaiduKeyGenerate getBaiduKey;

    @Test
    public void atest() throws Exception {
        System.out.println(BaiduApiConstant.API_KEY);
        getBaiduKey.getAuth();
    }
}
