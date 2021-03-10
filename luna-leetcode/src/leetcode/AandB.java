package leetcode;

/**
 * @author luna@mac
 * @className leetcode.AandB.java
 * @description TODO
 * @createTime 2020年12月18日 13:45:00
 */
public class AandB {
    /**
     * a + b but don't use '+'
     */
    public static void main(String[] args) {
        /**
         * eg：a + b = a ^ b + (a & b) << 1
         */
        System.out.println(sum(1, 3));
    }

    public static int sum(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        int sum = a ^ b;
        int carry = (a & b) << 1;
        return sum(sum, carry);
    }
}
