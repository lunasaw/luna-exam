package com.luna.springdemo;


import com.luna.baidu.config.BaiduConfigValue;
import com.luna.common.spring.SpringUtils;
import com.luna.tencent.config.TencentConfigValue;
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
	private SpringUtils springUtils;

	@Autowired
	private TencentConfigValue tencentConfigValue;

	@Autowired
	private BaiduConfigValue baiduConfigValue;

	@Test
	public void atest() throws Exception {
		TencentConfigValue bean = springUtils.getBean("tencentConfigValue");
		System.out.println(bean.getSecretKey());
		System.out.println(tencentConfigValue.getSecretKey());
		System.out.println(baiduConfigValue.getAppKey());
		System.out.println(baiduConfigValue.getBaiduKey());
	}
}
