package leetcode;

/**
 * @author luna@mac
 * @className leetcode.UniquePathsTwo.java
 * @description TODO
 * <p>
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * <p>
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * <p>
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * <p>
 * <p>
 * <p>
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * 输出：2
 * 解释：
 * 3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 * @createTime 2021年01月19日 21:50:00
 */
public class UniquePathsTwo {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int n = obstacleGrid.length, m = obstacleGrid[0].length;

        // 路径计算滚动数组
        int f[] = new int[m];

        // 初始点判断
        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;

        // 宽
        for (int i = 0; i < n; i++) {
            // 长
            for (int j = 0; j < m; j++) {
                // 结点是否有障碍
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                // 如果上一个网格不在上边框上 并且上一个结点没有障碍物
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    // 路径等与前一个路径加当前路径=1
                    f[j] += f[j - 1];
                }
            }
        }
        return f[m - 1];

    }

    public static void main(String[] args) {
        int i = new UniquePathsTwo().uniquePathsWithObstacles(new int[][]{{0, 1}});
        System.out.println(i);
    }

}
