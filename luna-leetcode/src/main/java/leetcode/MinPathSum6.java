package leetcode;

/**
 * @author luna@mac
 * @className leetcode.MinPathSum.java
 * @description TODO
 * @createTime 2021年01月20日 23:55:00
 */
public class MinPathSum6 {


    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 状态转移

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j != 0) { // 第一行
                    grid[i][j] = grid[i][j] + grid[i][j - 1];
                } else if (i != 0 && j == 0) {
                    grid[i][j] = grid[i][j] + grid[i - 1][j];
                } else if (i != 0 && j != 0) {
                    grid[i][j] = grid[i][j] + Math.min(grid[i][j - 1], grid[i - 1][j]);
                }
            }
        }

        return grid[m - 1][n - 1];
    }


    public static void main(String[] args) {

    }
}
