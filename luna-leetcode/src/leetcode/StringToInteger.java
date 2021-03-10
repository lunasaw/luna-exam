package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/7 19:44
 */
public class StringToInteger {

	/**
	 * 字符传转为数字
	 *
	 * @param str
	 * @return
	 */
	public static int stringToInteger(String str) {
		str = str.trim();
		if (str == null || str.length() == 0) {
			return 0;
		}
		char firstChar = str.charAt(0);
		int start = 0;
		int sign = 1;
		long res = 0;
		if (firstChar == '-') {
			sign = -1;
			start++;
		}
		if (firstChar == '+') {
			sign = 1;
			start++;
		}
		for (int i = start; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return (int) res * sign;
			}
			res = res * 10 + str.charAt(i) - '0';
			if (sign == -1 && res > Integer.MAX_VALUE) {
				return Integer.MIN_VALUE;
			}
			if (sign == 1 && res > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
		}
		return (int) res * sign;
	}

	public static void main(String[] args) {
		int i = stringToInteger(" ");
		System.out.println(i);
	}
}
