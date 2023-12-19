package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/18
 * @description:
 */
public class IsSubsequence_392 {

    /**
     * s是否为t的子序列
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        char[] charArray = s.toLowerCase().toCharArray();
        int temp = 0;
        for (int i = 0; i < charArray.length; i++) {
            int i1 = t.indexOf(charArray[i], temp);
            t = t.replaceFirst(String.valueOf(charArray[i]), " ");
            temp = Math.max(temp, i1);
            if (i1 < 0 || i1 < temp) {
                return false;
            }
        }

        return true;
    }

    public static boolean isSubsequence2(String s, String t) {
        int sIndex = 0;
        int tIndex = 0;

        if (s.length() == 1){
            return t.indexOf(s.charAt(0)) > 0;
        }


        while (sIndex <= s.length() - 1 && tIndex <= t.length() - 1){
            if (s.charAt(sIndex) == t.charAt(tIndex)){
                tIndex++;
                sIndex++;
            } else {
                tIndex++;
            }
        }

        if (sIndex != s.length()){
            return false;
        }
        return true;
    }

        public static void main(String[] args) {
        String s = "abc";
        String t = "ahbgdc";
        boolean subsequence = isSubsequence2(s, t);
        System.out.println(subsequence);
    }
}
