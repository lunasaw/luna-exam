package com.luna.practice.gw.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Luna@win10
 * @date 2020/3/31 21:09
 */
public class Test_3_31 {

	public static boolean isNumeric(String str) {
		String regEx = "^-?[0-9]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void main(String[] args) {
		String str = "123";
		//true
		System.out.println(isNumeric(str));

		str = "-123";
		//true
		System.out.println(isNumeric(str));

		str = "abc";
		//false
		System.out.println(isNumeric(str));
	}

}
