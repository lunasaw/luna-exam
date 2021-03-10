package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/14 23:12
 */
public class LongestCommonPrefix {

	/**
	 * 最长公共前子串
	 * @param strs
	 * @return
	 */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String res = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(res) != 0) {
                res = res.substring(0, res.length() - 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[] {"flower", "flow", "flight"}));
    }
}
