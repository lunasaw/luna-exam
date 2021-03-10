package leetcode;

import java.util.Arrays;

/**
 * @author luna@mac
 * @className leetcode.MinPathSum.java
 * @description TODO
 * @createTime 2021年01月20日 23:55:00
 */
public class MinPathSum4 {


    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[][] dp = new int[m][n];

        dp[m - 1][n - 1] = grid[m - 1][n - 1];
        // 状态转移

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // 最后一行
                if (i == m - 1 && j != n - 1) {
                    dp[i][j] = grid[i][j] + dp[i][j + 1];
                    // 最后一列
                } else if (i != m - 1 && j == n - 1) {
                    dp[i][j] = grid[i][j] + dp[i + 1][j];
                } else if (i != m - 1 && j != n - 1) {
                    dp[i][j] = grid[i][j] + Math.min(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        return dp[0][0];
    }


    public static void main(String[] args) {

    }
}
