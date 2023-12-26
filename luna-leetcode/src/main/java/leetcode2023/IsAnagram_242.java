package leetcode2023;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author luna
 * @date 2023/12/26
 */
public class IsAnagram_242 {

    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     *
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     *
     *
     *
     * 示例 1:
     *
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * 示例 2:
     *
     * 输入: s = "rat", t = "car"
     * 输出: false
     * @param s
     * @param t
     * @return
     */
    public static boolean isAnagram(String s, String t) {
        HashMap<Object, Object> s2t = new HashMap<>();
        HashMap<Object, Object> t2s = new HashMap<>();

        if (s.length() != t.length()){
            return false;
        }

        char[] charArray = s.toCharArray();
        char[] charArray1 = t.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char x = charArray[i];
            if (s2t.containsKey(x)){
                s2t.put(x, (Integer)s2t.get(x) + 1);
            } else {
                s2t.put(x, 1);
            }

            char y = charArray1[i];

            if (t2s.containsKey(y)){
                t2s.put(y, (Integer)t2s.get(y) + 1);
            } else {
                t2s.put(y, 1);
            }
        }

        boolean res = true;

        for (Object k : s2t.keySet()) {
            Integer o = (Integer) t2s.get(k);

            Object o1 = s2t.get(k);
            if (!Objects.equals(o1, o)){
                return false;
            }
        }

        return res;
    }

    public static boolean isAnagram2(String s, String t) {
        if(s.length() != t.length())
            return false;
        int[] alpha = new int[26];
        for(int i = 0; i< s.length(); i++) {
            alpha[s.charAt(i) - 'a'] ++;
            alpha[t.charAt(i) - 'a'] --;
        }
        for(int i=0;i<26;i++)
            if(alpha[i] != 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        String s = "anagram";
        String t = "nagaram";
        System.out.println(isAnagram(s, t));
    }
}
