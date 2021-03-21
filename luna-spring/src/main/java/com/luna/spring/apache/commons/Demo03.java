package com.luna.spring.apache.commons;



import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * @author Luna@win10
 * @date 2020/5/18 21:01
 */
public class Demo03 {

	public static void main(String[] args) {
		Date date = null;
		try {
			date=DateUtils.parseDate("2018-12-23 12:34:23",  Locale.TRADITIONAL_CHINESE,"yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String format = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
		System.out.println(date);
		System.out.println(format);

	}

}
