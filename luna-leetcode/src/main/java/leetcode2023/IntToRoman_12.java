package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/14
 * @description:
 */
public class IntToRoman_12 {

    /**
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     */
    public static String intToRoman(Integer roman) {

        int[] romanNum = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanChars = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < romanNum.length; i++) {
            String cuttentChar = romanChars[i];
            while (roman >= romanNum[i]) {
                roman -= romanNum[i];
                str.append(cuttentChar);
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int num = 1994;
        String s = intToRoman(num);
        System.out.println(s);
    }
}
