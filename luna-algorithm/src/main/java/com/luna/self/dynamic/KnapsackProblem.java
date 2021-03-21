package com.luna.self.dynamic;

import java.util.Arrays;

/**
 * @author luna@mac
 * @className KnapsackProblem.java
 * @description TODO 0-1背包问题
 * @createTime 2021年03月21日 16:43:00
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        /** 物品重量 */
        int[] weight = {1, 4, 3};

        /** 物品价值 */
        int[] value = {1500, 3000, 2000};

        /** 背包容量 */
        int m = 4;

        /** 物品个数 */
        int n = value.length;

        // v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n + 1][m + 1];
        // 为了记录放入商品的情况，我们定一个二维数组
        int[][] path = new int[n + 1][m + 1];

        // 初始化第一行和第一列, 这里在本程序中，可以不去处理，因为默认就是0
        // 将第一列设置为0
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        // 将第一行设置0
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;
        }

        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[0].length; j++) {
                if (weight[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                    if (v[i - 1][j] < value[i - 1] + v[i - 1][j - weight[i - 1]]) {
                        v[i][j] = value[i - 1] + v[i - 1][j - weight[i - 1]];
                        // 把当前的情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }

        for (int[] ints : v) {
            System.out.println(Arrays.toString(ints));
        }

        // 行的最大下标
        int i = path.length - 1;
        // 列的最大下标
        int j = path[0].length - 1;
        // 从path的最后开始找
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                // weight[i-1]
                j -= weight[i - 1];
            }
            i--;
        }

    }

}
