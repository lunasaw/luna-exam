package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/3 11:56
 */
public class ZigzagConversion {

	public static String convert(String s, int numRows) {
		char[] chars = s.toCharArray();
		StringBuilder[] builders = new StringBuilder[numRows];
		int len = s.length();
		//全局循环 i
		for (int i = 0; i < builders.length; i++) {
			builders[i] = new StringBuilder();
		}
		// 最大循环次数
		int i = 0;
		while (i < len) {
			//从上到下
			for (int idx = 0; idx < numRows && i < len; idx++) {
				builders[idx].append(chars[i++]);
			}
			for (int idx = numRows - 2; idx >= 1 && i < len; idx--) {
				builders[idx].append(chars[i++]);
			}
		}
		for (int i1 = 1; i1 < builders.length; i1++) {
			builders[0].append(builders[i1]);
		}
		return builders[0].toString();
	}

	public static void main(String[] args) {
		String s="PAYPALISHIRING";
		String convert = convert(s, 3);
		System.out.println(convert);
	}
}
