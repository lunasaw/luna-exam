package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/18
 * @description:
 */
public class IsPalindrome_125 {

    public static boolean isPalindrome(String s) {
        if (s.length() <= 1) {
            return true;
        }

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            if (!Character.isLetterOrDigit(leftChar)) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(rightChar)) {
                right--;
                continue;
            }

            if (Character.isLetterOrDigit(leftChar) && Character.isLetterOrDigit(rightChar)) {
                if (Character.toLowerCase(leftChar) != Character.toLowerCase(rightChar)) {
                    return false;
                }
                left++;
                right--;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "race a car";
        System.out.println(isPalindrome(s));
    }
}
