package com.luna.spring;

import com.alibaba.fastjson.JSON;
import com.luna.api.smms.api.ImageApiFromString;
import com.luna.api.smms.config.SmMsProperties;
import com.luna.api.smms.dto.UploadResultDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/5/6 12:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AliApiTest {
    @Autowired
    private SmMsProperties smMsConfigValue;

    @Test
    public void atest() throws Exception {
        List<UploadResultDTO> allHistory = ImageApiFromString.getAllHistory(smMsConfigValue.getAuthorizationCode());
        System.out.println(JSON.toJSONString(allHistory));
    }
}
