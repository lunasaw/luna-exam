package com.luna.spring.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Luna@win10
 * @date 2020/4/1 11:37
 */
public class Time {

	//new Date()所做的事情其实就是调用了System.currentTimeMillis()
	@Test
	public void aTest() {
	//获得系统的时间，单位为毫秒,转换为妙
		long totalMilliSeconds = System.currentTimeMillis();

		DateFormat dateFormatterChina = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);//格式化输出
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");//获取时区 这句加上，很关键。
		dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区


		long totalSeconds = totalMilliSeconds / 1000;


		//求出现在的秒
		long currentSecond = totalSeconds % 60;

		//求出现在的分
		long totalMinutes = totalSeconds / 60;
		long currentMinute = totalMinutes % 60;

		//求出现在的小时
		long totalHour = totalMinutes / 60;
		long currentHour = totalHour % 24;

		//显示时间
		System.out.println("总毫秒为： " + totalMilliSeconds);
		System.out.println(currentHour + ":" + currentMinute + ":" + currentSecond + " GMT");


		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(currentTime);
		System.out.println(formatter.format(date));
	}

}
