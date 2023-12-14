package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/14
 * @description:
 */
public class LongestCommonPrefix_14 {

    public String longestCommonPrefix(String[] strs) {
        StringBuilder result = new StringBuilder();

        char[] charArray = strs[0].toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            for (String str : strs) {
                if (str.length() <= i){
                    return result.toString();
                }
                if (str.charAt(i) != c) {
                    return result.toString();
                }
            }
            result.append(c);
        }

        return result.toString();
    }

    public static void main(String[] args) {

    }
}
