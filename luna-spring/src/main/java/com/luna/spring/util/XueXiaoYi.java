package com.luna.spring.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.collect.ImmutableMap;

import com.luna.common.net.HttpUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/4/28 11:30
 */
public class XueXiaoYi {

	static String HOST = "http://app.51xuexiaoyi.com";

	static String PATH = "/api/v1/searchQuestion";

	static String TOKEN = "bM1kRmsY64qC8CPxbqgEOnB90cxFh6zy6wmFzY7PTiLKSAk0RfIGPPCEOoIP";

	static String ZH_CN = "zh-cn";

	static String EN_US = "en-us";

	public static String getAnswer(String key, String language) throws IOException {

		String s = "{\"keyword\":\"" + key + "\"}";
		HttpResponse httpResponse = HttpUtils.doPost(HOST, PATH, ImmutableMap.of("Content-Type", "application/json", "Accept-Language", language
				, "token", TOKEN
		), null, s);
        String andGetResult = HttpUtils.checkResponseAndGetResult(httpResponse);
        JSONObject jsonObject = JSON.parseObject(andGetResult);
        String data = jsonObject.getString("data");
        List<JSONObject> datas = JSON.parseArray(data, JSONObject.class);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < datas.size(); i++) {
			String a = datas.get(i).get("a").toString();
			String q = datas.get(i).get("q").toString();
			buffer.append(q + "\n");
			buffer.append("答案===>" + a + "\n");
			buffer.append("==================================\n");
		}
		System.out.println(buffer);
		return "";
	}


	public static void main(String[] args) throws IOException {
		getAnswer("Usually ethnocentrism may cause many negative effects, while we have to admit that it may also have ______________ effects in some cases.", ZH_CN);
	}
}
