package leetcode;

/**
 * @author luna@mac
 * @className leetcode.AddBinary.java
 * @description TODO
 * <p>
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * <p>
 * 输入为 非空 字符串且只包含数字 1 和 0。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例 2:
 * <p>
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *  
 * <p>
 * 提示：
 * <p>
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 * @createTime 2021年01月25日 21:53:00
 */
public class AddBinary {

    public String addBinary(String a, String b) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = Math.max(a.length(), b.length()), carry = 0;

        for (int i = 0; i < n; i++) {
            carry += i < a.length() ? (a.charAt(a.length() - 1 - i) - '0') : 0;

            carry += i < b.length() ? (b.charAt(b.length() - 1 - i) - '0') : 0;

            stringBuffer.append(carry % 2 + '0');
            carry /= 2;
        }

        if (carry > 0) {
            stringBuffer.append('1');
        }

        return stringBuffer.reverse().toString();
    }

}
