package com.luna.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luna.media.javacv.CheckFace;
import com.luna.tencent.api.TencentMarketApi;
import com.luna.tencent.config.TencentConfigValue;
import com.luna.tencent.config.TencentPayMqConfigValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;


/**
 * @author Luna@win10
 * @date 2020/5/6 20:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TencentApiTest {

    @Autowired
    private TencentPayMqConfigValue tencentPayMqConfigValue;

    @Autowired
    private TencentConfigValue      tencentConfigValue;

    @Test
    public void atest() throws Exception {
        System.out.println(tencentPayMqConfigValue.getExchange());
        System.out.println(tencentConfigValue.getSecretid());
        JSONObject jsonObject = TencentMarketApi.checkIdByLuna(tencentConfigValue.getSkyEyeSecretid(),
            tencentConfigValue.getSkyEyeSecretkey(), "陈章月", "500384199911072412");
        System.out.println(JSON.toJSONString(jsonObject));
    }
}
