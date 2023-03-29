package leetcode;

/**
 * @author luna@mac
 * @className leetcode.UniquePaths.java
 * @description TODO
 * <p>
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * <p>
 * 问总共有多少条不同的路径？
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：m = 3, n = 7
 * 输出：28
 * @createTime 2021年01月18日 23:37:00
 */
public class UniquePaths {

    public int uniquePaths(int m, int n) {
        int[][] path = new int[m][n];

        for (int i = 0; i < m; i++) {
            path[i][0] = 1;
        }

        for (int i = 0; i < n; i++) {
            path[0][i] = 1;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                path[i][j] = path[i][j - 1] + path[i - 1][j];
            }
        }

        return path[m - 1][n - 1];
    }
}
