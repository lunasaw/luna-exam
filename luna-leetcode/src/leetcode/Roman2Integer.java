package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/13 14:59
 */
public class Roman2Integer {

	public static int romanToInt(String s) {
		if ("".equals(s) || s.length() == 0) {
			return 0;
		}
		int res = toNumber(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			if (toNumber(s.charAt(i)) > toNumber(s.charAt(i - 1))) {
				res += toNumber(s.charAt(i)) - 2 * toNumber(s.charAt(i - 1));
			} else {
				res += toNumber(s.charAt(i));
			}
		}
		return res;
	}

	private static int toNumber(char ch) {
		switch (ch) {
			case 'I':
				return 1;
			case 'V':
				return 5;
			case 'X':
				return 10;
			case 'L':
				return 50;
			case 'C':
				return 100;
			case 'D':
				return 500;
			case 'M':
				return 1000;
			default:
				return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(romanToInt("IV"));
	}
}
