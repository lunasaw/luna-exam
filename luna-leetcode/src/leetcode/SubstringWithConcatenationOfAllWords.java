package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luna@win10
 * @date 2020/6/1 20:49
 */
public class SubstringWithConcatenationOfAllWords {

    /**
     * 寻找words 任意组合单词出现位置
     * @param s
     * @param words
     * @return
     */
    public List<Integer> findSubstring(String s, String[] words) {
        if (s == null || words.length == 0) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();

        int n = words.length;
        int m = words[0].length();

        Map<String, Integer> map = new HashMap();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i <= s.length() - n * m; i++) {
            Map<String, Integer> copy = new HashMap(map);
            int k = n; // 单词数
            int j = i;
            while (k > 0) {
                String substring = s.substring(j, j + m);
                if (!copy.containsKey(substring) || copy.get(substring) < 1) {
                    break;
                }
                // 单词数-1
                copy.put(substring, copy.get(substring) - 1);
                k--;
                // 比对s 跳转下一个单词
                j += m;
            }
            // 如果单词完全匹配完成 起始位置放入返回值
            if (k == 0) {
                res.add(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SubstringWithConcatenationOfAllWords substringWithConcatenationOfAllWords =
            new SubstringWithConcatenationOfAllWords();
        substringWithConcatenationOfAllWords.findSubstring("barfoothefoobarman", new String[] {"foo", "bar"});
    }
}
