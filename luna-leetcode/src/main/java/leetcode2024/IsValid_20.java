package leetcode2024;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author luna
 * @date 2024/1/8
 */
public class IsValid_20 {

    private static final Map<Character, Character> map = new HashMap<Character, Character>() {{
        put('{', '}');
        put('[', ']');
        put('(', ')');
        put('?', '?');
    }};

    public static boolean isValid(String s) {
        if (s.isEmpty())
            return true;
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.empty() || c != stack.pop())
                return false;
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        String s = "({})";
        isValid(s);
    }
}
