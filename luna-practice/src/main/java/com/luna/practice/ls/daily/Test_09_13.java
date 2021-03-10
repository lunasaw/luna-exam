package com.luna.practice.ls.daily;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: Test_09_13
 * @Author: luna
 * @CreateTime: 2020/9/13 14:51
 * @Description:
 */
public class Test_09_13 {

    /**
     * 两数相加
     *
     * @param numStr1 数1
     * @param numStr2 数2
     * @return 结果
     */
    public static String add(String numStr1, String numStr2) {

        if (numStr1 == null || numStr1.length() == 0) {
            return numStr2;
        }

        if (numStr2 == null || numStr2.length() == 0) {
            return numStr1;
        }


        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1];
        //数字数组
        int[] numArray2 = new int[numLen2];


        // "12345"-> [5,4,3,2,1]
        for (int i = 0; i < numLen1; i++) {
            String c = numStr1.substring(i, i + 1);
            numArray1[numLen1 - i - 1] = Integer.parseInt(c);
            //低位存字符串尾部数字
        }
        for (int i = 0; i < numLen2; i++) {
            String c = numStr2.substring(i, i + 1);
            numArray2[numLen2 - i - 1] = Integer.parseInt(c);
            //低位存字符串尾部数字
        }


        int minLen = 0; //取长度小的数位数
        int maxLen = 0; //取长度大的数位数
        int[] maxArray = null; //长度大的数
        if (numLen1 < numLen2) {
            minLen = numLen1;
            maxLen = numLen2;
            maxArray = numArray2;
        } else {
            minLen = numLen2;
            maxLen = numLen1;
            maxArray = numArray1;
        }

        int[] resultArray = new int[maxLen + 1]; //考虑到可能会进位，多给一个元素空间

        //两数长度相同的部分，同位相加，超出9进1
        int added = 0;
        int i = 0;
        for (; i < minLen; i++) {
            int t = numArray1[i] + numArray2[i] + added; //两数相加，再加进位
            if (t > 9) {
                added = 1; //进1
                resultArray[i] = t - 10; //当前位计算结果
            } else {
                added = 0; //不进位
                resultArray[i] = t; //当前位计算结果
            }
        }
        //长度超出部分累加
        for (; i < maxLen; i++) {
            int t = maxArray[i] + added; //多余位数加上进位
            if (t > 9) {
                added = 1; //进1
                resultArray[i] = t - 10; //当前位计算结果
            } else {
                added = 0; //不进位
                resultArray[i] = t; //当前位计算结果
            }
        }
        resultArray[i] = added; //最高位

        //拼接结果 [1,4,8,2,0] -> 2841
        StringBuilder builder = new StringBuilder();
        for (int n = resultArray.length - 1; n >= 0; n--) {
            //如果最高位为0,移除
            if (n == resultArray.length - 1 && resultArray[resultArray.length - 1] == 0) {
                continue; //跳过
            } else {
                builder.append(resultArray[n]);
            }
        }

        return builder.toString();
    }

    /**
     * 两数相减
     *
     * @param numStr1 数1
     * @param numStr2 数2
     * @return 结果
     */
    public static String subtract(String numStr1, String numStr2) {
        if (numStr1 == null || numStr1.length() == 0) {
            return numStr2;
        }

        if (numStr2 == null || numStr2.length() == 0) {
            return numStr1;
        }


        int numLen1 = numStr1.length();
        int numLen2 = numStr2.length();

        int[] numArray1 = new int[numLen1]; //数字数组
        int[] numArray2 = new int[numLen2];


        // "12345"-> [5,4,3,2,1]
        for (int i = 0; i < numLen1; i++) {
            String c = numStr1.substring(i, i + 1);
            numArray1[numLen1 - i - 1] = Integer.parseInt(c); //低位存字符串尾部数字
        }
        for (int i = 0; i < numLen2; i++) {
            String c = numStr2.substring(i, i + 1);
            numArray2[numLen2 - i - 1] = Integer.parseInt(c); //低位存字符串尾部数字
        }


        int minLen = 0; //取长度小的数位数
        int maxLen = 0; //取长度大的数位数
        int[] maxArray = null; //数值大的数
        if (numLen1 < numLen2) {
            minLen = numLen1;
            maxLen = numLen2;
            maxArray = numArray2;
        } else {
            minLen = numLen2;
            maxLen = numLen1;
            maxArray = numArray1;
            if (numLen1 == numLen2) { //等于
                maxArray = getMaxNumber(numArray1, numArray2);
            }
        }
        int[] minArray = maxArray == numArray1 ? numArray2 : numArray1; //数值小的数

        int[] resultArray = new int[maxLen];

        //大数-小数，同位相减，小于0借位
        int subtracted = 0;
        int i = 0;
        for (; i < minLen; i++) {
            int t = maxArray[i] - minArray[i] - subtracted; //两数相减，再减借位
            if (t < 0) {
                subtracted = 1; //向高位借1，暂存起来
                resultArray[i] = t + 10; //当前位计算结果（借1相当于借了10）
            } else {
                subtracted = 0; //不借位
                resultArray[i] = t; //当前位计算结果
            }
        }
        //大数超出部分减掉借位
        for (; i < maxLen; i++) {
            int t = maxArray[i] - subtracted; //多余位数减掉借位
            if (t < 0) {
                subtracted = 1; //进1
                resultArray[i] = t + 10; //当前位计算结果
            } else {
                subtracted = 0; //不借位
                resultArray[i] = t; //当前位计算结果
            }
        }

        //拼接结果 [1,4,8,2,0] -> 2841
        StringBuilder builder = new StringBuilder();
        boolean highBitNotEqualZero = false; //存在高位不为0的情况，低位0保留
        for (int n = resultArray.length - 1; n >= 0; n--) {
            //如果高位为0,移除
            if (resultArray[n] == 0 && !highBitNotEqualZero && n != 0) { //高位无用的0去除
                continue; //跳过
            } else {
                highBitNotEqualZero = true; //找到不为0的位
                builder.append(resultArray[n]);
            }
        }

        if (maxArray == numArray1) { //第一个数大或相等

        } else {  //第一个数小于第二个数，相减为负数
            builder.insert(0, "-");
        }

        return builder.toString();
    }

    /**
     * 计算大数
     *
     * @param numArray1 数1
     * @param numArray2 数2
     * @return 大数
     */
    public static int[] getMaxNumber(int[] numArray1, int[] numArray2) {
        for (int i = numArray1.length - 1; i >= 0; i--) {
            if (numArray1[i] > numArray2[i]) {
                return numArray1;
            } else {
                if (numArray1[i] == numArray2[i]) {
                    continue; //待继续比较
                } else {
                    return numArray2;
                }
            }
        }
        return numArray1;
        //全部相等，返回第一个
    }

    public static void main(String[] args) {
        System.out.println("用递归实现斐波那契数列  如下：");
        for (int a = 1; a <= 10; a++) {
            System.out.println("=========================");
            System.out.println((int) Math.pow(2, a) + "=>" + fib((int) Math.pow(2, a)));
        }

        System.out.println("用非递归实现斐波那契数列  如下：");
        for (int a = 1; a <= 10; a++) {
            System.out.println("=========================");
            System.out.println((int) Math.pow(2, a) + "=>" + fib3((int) Math.pow(2, a)));
        }
    }

    public static String fib(int n) {
        return fib1(n, new HashMap());
    }

    public static String fib1(int n, Map<Integer, String> map) {
        if (n <= 2) {
            return "1";
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        String left = fib1(n - 1, map);
        map.put(n - 1, left);
        String right = fib1(n - 2, map);
        map.put(n - 2, right);
        String middle = add(left, right);
        map.put(n, middle);
        return add(left, right);
    }


    /**
     * 迭代实现
     *
     * @param f
     * @return
     */
    public static String fib3(int f) {
        String first = "0";
        String second = "1";
        for (int i = 1; i <= f; i++) {
            second = add(first, second);
            first = subtract(second, first);
        }
        return first;
    }

}
