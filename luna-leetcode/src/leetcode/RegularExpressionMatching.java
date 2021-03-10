package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/10 21:56
 */
public class RegularExpressionMatching {

	/**
	 * 正则匹配
	 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
	 * <p>
	 * '.' 匹配任意单个字符
	 * '*' 匹配零个或多个前面的那一个元素
	 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
	 *
	 * @param s
	 * @param p
	 * @return
	 */
	public static boolean isMatch(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
		dp[0][0] = true;
		for (int i = 0; i < p.length(); i++) {
			if (i > 0 && p.charAt(i) == '*' && dp[0][i - 1]) {
				dp[0][i + 1] = true;
			}
		}
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < p.length(); j++) {
				//每个位置都相等
				if (i > 0 && p.charAt(j) == s.charAt(i)) {
					//给下一个位置先赋为true
					dp[i + 1][j + 1] = dp[i][j];
				}
				if (i > 0 && p.charAt(j) == '.') {
					dp[i + 1][j + 1] = dp[i][j];
				}
				if (i > 0 && p.charAt(j) == '*') {
					if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
						dp[i + 1][j + 1] = dp[i + 1][j - 1];
					} else {
						dp[i + 1][j + 1] = (dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j - 1]);
					}
				}
			}
		}
		return dp[s.length()][p.length()];
	}

	public static void main(String[] args) {
		System.out.println(isMatch("aa","a"));
	}
}
