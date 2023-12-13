package leetcode2023;

import java.util.HashMap;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/13
 * @description:
 */
public class RomanToInt_13 {

    /**
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     *
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            Integer integer = map.get(charArray[i]);
            System.out.println(integer);
        }

        int total = 0;
        for (int i = charArray.length - 1; i >= 0; i--) {
            if (i == 0){
                total += getValue(charArray[i]);
                break;
            }
            if ((charArray[i] == 'V' || charArray[i] == 'X') && charArray[i - 1] == 'I'){
                total += getValue(charArray[i]) - getValue('I');
                i--;
            } else if ((charArray[i] == 'L' || charArray[i] == 'C') && charArray[i - 1] == 'X'){
                total += getValue(charArray[i]) - getValue('X');
                i--;
            } else if ((charArray[i] == 'D' || charArray[i] == 'M') && charArray[i - 1] == 'C'){
                total += getValue(charArray[i]) - getValue('C');
                i--;
            } else {
                total += getValue(charArray[i]);
            }
        }
        return total;
    }

    private static int getValue(char ch) {
        switch(ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        String roman = "MCMXCIV";
        int i = romanToInt(roman);
        System.out.println(i);
    }
}
