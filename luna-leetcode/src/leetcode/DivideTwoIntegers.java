package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/31 21:23
 */
public class DivideTwoIntegers {

    public int divide(int dividend, int divisor) {
        // 将被除数和除数强转成 long 类型做运算，避免溢出。
        long ans = divide((long) dividend, (long) (divisor));
        // 定义边界值判断最终结果是否越界，表示 Integer.MIN_VALUE / (-1)。
        long max = 2147483648L;
        if (ans == max) {
            return Integer.MAX_VALUE;
        } else {
            return (int) ans;
        }
    }

    public long divide(long dividend, long divisor) {
        long ans = 1;
        long symbol = 1;
        // 将被除数和除数都转成正数运算并且记录最终结果符号。
        if (dividend < 0) {
            symbol = -symbol;
            dividend = -dividend;
        }
        if (divisor < 0) {
            symbol = -symbol;
            divisor = -divisor;
        }
        // 记录当前一轮开始的被除数和除数。
        long currDividend = dividend;
        long currDivisor = divisor;
        // 不够除则直接返回 0 。
        if (dividend < divisor) {
            return 0;
        }
        // 循环前被除数减去初始化的一倍除数。
        dividend -= divisor;
        while (divisor <= dividend) {
            // 将当前一轮的运算结果翻倍。
            ans += ans;
            // 将当前一轮的除数翻倍。
            divisor += divisor;
            // 每轮被除数提前减一次除数，下一轮还能进循环表示当前除数可以翻倍。
            dividend -= divisor;
        }
        // 递归运算以当前除数取模的结果去除以原来的除数。
        long a = ans + divide(currDividend - divisor, currDivisor);
        return symbol * a;
    }
    public static void main(String[] args) {
        DivideTwoIntegers divideTwoIntegers = new DivideTwoIntegers();
        divideTwoIntegers.divide(-2147483648, -1);
    }
}
