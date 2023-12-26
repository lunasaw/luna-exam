package leetcode2023;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luna
 * @date 2023/12/26
 */
public class IsIsomorphic_205 {

    /**
     * 给定两个字符串 s 和 t ，判断它们是否是同构的。
     *
     * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     *
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。
     * 不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
     *
     * 示例 1:
     *
     * 输入：s = "egg", t = "add"
     * 输出：true
     * 示例 2：
     *
     * 输入：s = "foo", t = "bar"
     * 输出：false
     * 示例 3：
     *
     * 输入：s = "paper", t = "title"
     * 输出：true
     * @param s
     * @param t
     * @return
     */
    public static boolean isIsomorphic(String s, String t) {
        Map<Character, Character> s2t = new HashMap<Character, Character>();
        Map<Character, Character> t2s = new HashMap<Character, Character>();

        char[] charArray = s.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char x = s.charAt(i);
            char y = t.charAt(i);

            if (t2s.containsKey(y) && t2s.get(y) != x){
                return false;
            }


            if (s2t.containsKey(x) && s2t.get(x) != y){
                return false;
            }

            s2t.put(x, y);
            t2s.put(y,x);

        }

        return true;
    }

    public static void main(String[] args) {
        String s = "paper";
        String t = "title";
        System.out.println(isIsomorphic(s, t));
    }
}
