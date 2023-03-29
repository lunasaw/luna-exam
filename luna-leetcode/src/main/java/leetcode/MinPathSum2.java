package leetcode;

/**
 * @author luna@mac
 * @className leetcode.MinPathSum.java
 * @description TODO
 * @createTime 2021年01月20日 23:55:00
 */
public class MinPathSum2 {

    // 方向 「下，右」
    private int[][] dirs = {{1, 0}, {0, 1}};

    public int minPathSum(int[][] grid) {
        return dfs(grid, 0, 0);
    }

    private int dfs(int[][] grid, int row, int col) {
        // 结束条件
        if (row == grid.length - 1 && col == grid[0].length - 1) {
            return grid[row][col];
        }

        int minPathSum = Integer.MAX_VALUE;

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

            int childMinPathSum = dfs(grid, nextRow, nextCol);
            minPathSum = Math.min(childMinPathSum, minPathSum);

        }
        // 计算叶子到本结点到最小路径和
        return minPathSum + grid[row][col];
    }

    public static void main(String[] args) {

    }
}
