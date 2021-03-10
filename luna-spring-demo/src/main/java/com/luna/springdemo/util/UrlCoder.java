package com.luna.springdemo.util;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/3/30 20:24
 */
public class UrlCoder {

	/**
	 * 1.URLEncoder.encode(String s, String enc) 
	 * 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式 
	 *
	 * URLDecoder.decode(String s, String enc) 
	 * 使用指定的编码机制对 application/x-www-form-urlencoded 字符串解码。 
	 *
	 * 2.发送的时候使用URLEncoder.encode编码，接收的时候使用URLDecoder.decode解码，都按指定的编码格式进行编码、解码，可以保证不会出现乱码
	 *
	 * 3.主要用来http get请求不能传输中文参数问题。http请求是不接受中文参数的。
	 *
	 * 这就需要发送方，将中文参数encode，接收方将参数decode，这样接收方就能收到准确的原始字符串了。
	 */
	@Test
	public void aTest() {
		String testString = "陈章月";
		try
		{
			String encoderString = URLEncoder.encode(testString, "utf-8");
			System.out.println(encoderString);
			String decodedString = URLDecoder.decode(encoderString, "utf-8");
			System.out.println(decodedString);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void bTest() throws URISyntaxException {
		List<NameValuePair> parse = URLEncodedUtils.parse(new URI("http://luna.iszychen.club/?a=1&b=2"), "utf-8");
		// TODO 解析路径后的参数
		System.out.println(parse);
	}

}
