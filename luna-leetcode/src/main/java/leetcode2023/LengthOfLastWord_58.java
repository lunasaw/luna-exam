package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/14
 * @description:
 */
public class LengthOfLastWord_58 {

    public int lengthOfLastWord(String s) {
        String[] strings = s.split(" ");

        for (int i = strings.length - 1; i >= 0; i--) {
            if (!strings[i].contains(" ")) {
                return strings[i].length();
            }
        }

        return 0;
    }


    public static int lengthOfLastWord2(String s) {
        if (s.length() == 1 && s.charAt(0) != ' '){
            return 1;
        }
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' '){
            end --;
        }
        int start = end;
        while (start >= 0 && s.charAt(start) != ' '){
            start --;
        }

        return end - start;
    }

    public static void main(String[] args) {
        String s = "hello world ";
        int i = lengthOfLastWord2(s);
        System.out.println(i);
    }
}
