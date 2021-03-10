package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/2 11:33
 */
public class LongestPalindromicSubstring {

	/**
	 * 最长回文子串
	 *
	 * @param s
	 * @return
	 */
	public static String longestPalindromicSubstring(String s) {
		if (s.length() < 2) {
			return s;
		}
		String res = "";
		boolean[][] bp = new boolean[s.length()][s.length()];
		int max = 0;
		for (int j = 0; j < s.length(); j++) {
			for (int i = 0; i <= j; i++) {
				bp[i][j] = s.charAt(i) == s.charAt(j) && ((j - i) <= 2 || bp[i + 1][j - 1]);
				if (bp[i][j]) {
					if (j - i + 1 > max) {
						max = j - i + 1;
						res = s.substring(i, j + 1);
					}
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		String s = "ac";
		String s1 = longestPalindromicSubstring(s);
		System.out.println(s1);
	}
}
