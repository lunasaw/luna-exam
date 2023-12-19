package leetcode2023;

import java.util.Arrays;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/14
 * @description:
 */
public class ReverseWords_151 {

    public static String reverseWords(String s) {
        String[] strings = s.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            String trim = strings[i].trim();
            if (trim.length() <= 0) {
                continue;
            } else {
                result.append(trim).append(" ");
            }
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        String s = "  hello world  ";
        System.out.println(reverseWords(s));
    }
}
