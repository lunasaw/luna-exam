package leetcode2023;

import java.util.ArrayList;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/14
 * @description:
 */
public class ConvertLine_6 {

    public static String convert(String s, int numRows) {
        int length = s.length();
        if (length < 2) {
            return s;
        }

        ArrayList<Object> list = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            list.add(new StringBuilder());
        }
        int flag = -1;
        int i = 0;
        for (char c : s.toCharArray()) {
            StringBuilder stringBuilder = (StringBuilder) list.get(i);
            stringBuilder.append(c);
            if (i == 0 || i == numRows - 1) {
                flag = -flag;
            }
            i += flag;
        }

        StringBuilder result = new StringBuilder();
        for (Object o : list) {
            result.append(o.toString());
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        convert(s, 4);
    }
}
