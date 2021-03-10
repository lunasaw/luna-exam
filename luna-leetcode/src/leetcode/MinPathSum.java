package leetcode;

/**
 * @author luna@mac
 * @className leetcode.MinPathSum.java
 * @description TODO
 * @createTime 2021年01月20日 23:55:00
 */
public class MinPathSum {

    private int minPathSum = Integer.MAX_VALUE;

    // 方向 「下，右」
    private int[][] dirs = {{1, 0}, {0, 1}};

    public int minPathSum(int[][] grid) {
        dfs(grid, 0, 0, grid[0][0]);
        return minPathSum;
    }

    private void dfs(int[][] grid, int row, int col, int sum) {
        // 结束条件
        if (row == grid.length - 1 && col == grid[0].length - 1) {
            minPathSum = Math.min(sum, minPathSum);
            return;
        }

        // 每次只有两个方向可以选择 右 或者 下
        for (int[] dir : dirs) {
            // 行
            int nextRow = dir[0] + row;
            // 列
            int nextCol = dir[1] + col;

            if (nextRow < 0 || nextCol < 0 || nextCol >= grid[0].length || nextRow >= grid.length) {
                // 剪枝
                continue;
            }

            //
            sum += grid[nextRow][nextCol];

            dfs(grid, nextRow, nextCol, sum);

            // 回溯
            sum -= grid[nextRow][nextCol];
        }
    }

    public static void main(String[] args) {

    }
}
