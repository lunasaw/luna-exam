package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luna@win10
 * @date 2020/4/30 10:50
 */
public class lengthOfLongestSubstring {

	/**
	 *  求最长子串
	 * @param s
	 * @return
	 */
	public static int lengthOfLongestSubstring(String s) {
		int nes=0, n = s.length();
		Map<Character, Integer> map = new HashMap<>(n);
		for (int i = 0, j = 0; j < n; j++) {
			if (map.containsKey(s.charAt(j))){
				i=Math.max(map.get(s.charAt(j)),i);
			}
			nes=Math.max(nes,j-i+1);
			map.put(s.charAt(j),j+1);
		}
		return nes;
	}

	public static void main(String[] args) {
		int i = lengthOfLongestSubstring("abddqwrea");
		System.out.println(i);
	}
}
