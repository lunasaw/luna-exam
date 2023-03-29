package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/5/23 14:31
 */
public class GenerateParentheses {

    /**
     * 给定数字对的括号 返回所有括号组合形式
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {

        List<String> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        helper(res, "", n, n);
        return res;
    }

    private void helper(List<String> res, String s, int left, int right) {
        if (left > right) {
            return;
        }
        if (left == 0 && right == 0) {
            res.add(s);
        }
        if (left > 0) {
            helper(res, s + "(", left - 1, right);
        }
        if (right > 0) {
            helper(res, s + ")", left, right - 1);
        }
    }

    public static void main(String[] args) {
        GenerateParentheses generateParentheses = new GenerateParentheses();
        System.out.println(generateParentheses.generateParenthesis(3));
    }
}
