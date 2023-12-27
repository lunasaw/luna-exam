package leetcode2023;

import java.util.HashSet;
import java.util.Set;

/**
 * @author luna
 * @date 2023/12/27
 */
public class IsHappy_202 {

    private static Integer getNext(int n) {
        int total = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            total += d * d;
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(IsHappy_202.isHappy(2));
    }

    /**
     * 编写一个算法来判断一个数 n 是不是快乐数。
     * <p>
     * 「快乐数」 定义为：
     * <p>
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果这个过程 结果为 1，那么这个数就是快乐数。
     * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
     * <p>
     * <p>
     * <br/>
     * 示例 1：
     * <br/>
     * 输入：n = 19
     * 输出：true
     * <br/>
     * 解释：
     * <br/>
     * 1<sup>2</sup> + 9<sup>2</sup> = 82
     * <br/>
     * 8<sup>2</sup> + 2<sup>2</sup> = 68
     * <br/>
     * 6<sup>2</sup> + 8<sup>2</sup> = 100
     * <br/>
     * 1<sup>2</sup> + 0<sup>2</sup> + 0<sup>2</sup> = 1
     * <br/>
     * 示例 2：
     * <p>
     * 输入：n = 2
     * 输出：false
     *
     * @param n
     * @return
     */
    public static boolean isHappy(int n) {

        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

}
