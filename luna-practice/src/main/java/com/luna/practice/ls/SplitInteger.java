package com.luna.practice.ls;

/**
 * @author QBS @win10
 * @version 1.0
 * @date2020/10/8 22:31
 * 类说明
 */

public class SplitInteger {
    /**
     * 正整数加法不同的分解法
     *
     * @param sum：和
     * @param max：最大值
     * @param data：记录不同的加法形式
     * @param index：加法分解数的最大个数
     * @return 分解个数
     */
    public static int splitInteger(int sum, int max, int[] data, int index) {
        if (max > sum) max = sum;
        if (sum < 1 || max < 1) return 0;
        if (sum == 1 || max == 1) {
            if (sum == 1) {
                data[index] = sum;
                print(data, index + 1);
            } else {
                for (int i = 0; i < sum; i++) {
                    data[index++] = max;
                }
                print(data, index);
            }
            return 1;
        }
        if (sum == max) {
            data[index] = max;
            print(data, index + 1);
            return 1 + splitInteger(sum, max - 1, data, index);
        } else if (sum > max) {
            data[index] = max;
            //一定注意这里的先后顺序
            return splitInteger(sum - max, max, data, index + 1) + splitInteger(sum, max - 1, data, index);
        } else {
            //sum < max
            return splitInteger(sum, sum, data, index);
        }
    }

    //打印数组
    public static void print(int[] data, int index) {
        for (int i = 0; i < index - 1; i++) {
            System.out.print(data[i] + "+");
        }
        System.out.println(data[index-1]);
    }

    /**
     * 正整数加法不同分解的个数:最大值为max，和为sum的加法个数
     * 递归形式： f(sum, max) = f(sum-max, max) + f(sum, max-1);
     * @param sum
     * @param max
     * @return
     */
    public static int count(int sum, int max) {
        if (sum < 1 || max < 1) return 0;
        else if (sum == 1 || max == 1) {
            return 1;
        } else if (sum < max) {
            return count(sum, sum);
        } else if (sum == max) {
            return 1 + count(sum, sum - 1);
        } else {
            return count(sum, max - 1) + count(sum - max, max);
        }
    }

    public static void main(String[] args) {
        int n = 4;
        int[] data = new int[n];
        System.out.println("正整数\'" + n + "\'可以分解为如下不同的加法形式：");
        System.out.println("正整数\'" + n + " \'加法分解个数为：\t" + splitInteger(n, n, data,0));

    }

}