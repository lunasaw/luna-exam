package leetcode2023;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author luna
 * @date 2023/12/26
 */
public class WordPattern_290 {

    /**
     * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
     *
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。
     *
     *
     *
     * 示例1:
     *
     * 输入: pattern = "abba", s = "dog cat cat dog"
     * 输出: true
     * 示例 2:
     *
     * 输入:pattern = "abba", s = "dog cat cat fish"
     * 输出: false
     * 示例 3:
     *
     * 输入: pattern = "aaaa", s = "dog cat cat dog"
     * 输出: false
     * @param pattern
     * @param s
     * @return
     */
    public boolean wordPattern(String pattern, String s) {
        String[] s1 = s.split(" ");
        char[] charArray = pattern.toCharArray();

        if (s1.length != charArray.length){
            return false;
        }

        HashMap<String, Character> pattern2s = new HashMap<>();
        HashMap<Character, String> s2pattern = new HashMap<>();

        for (int i = 0; i < charArray.length; i++) {
            String y = s1[i];
            char x = charArray[i];

            if (pattern2s.containsKey(y) && pattern2s.get(y) != x){
                return false;
            }
            if (s2pattern.containsKey(x) && !Objects.equals(s2pattern.get(x), y)){
                return false;
            }

            s2pattern.put(x, y);
            pattern2s.put(y,x);
        }

        return true;
    }
}
