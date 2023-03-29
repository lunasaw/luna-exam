package leetcode;

/**
 * @author luna@mac
 * @className leetcode.GenerateMatrix.java
 * @description TODO
 * 给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
 *
 * 示例:
 *
 * 输入: 3
 * 输出:
 * [
 * [ 1, 2, 3 ],
 * [ 8, 9, 4 ],
 * [ 7, 6, 5 ]
 * ]
 *
 * @createTime 2021年01月15日 21:01:00
 */
public class GenerateMatrix {

    public int[][] generateMatrix(int n) {
        int l =0 , r = n -1, t =0 ,b = n -1;

        int[][] ints = new int[n][n];

        int num =1 ,tar = n * n;

        while (num<=tar) {
            // 从左到右
            for (int i = l; i <= r; i++) {
                ints[t][i] = num ++ ;
            }
            t ++;
            // 从上倒下
            for (int i = t; i <= b; i++) {
                ints [i][r] = num ++;
            }
            r --;
            // 从右到左
            for (int i = r; i >= l; i--) {
                ints [b][i] = num ++;
            }
            b --;
            // 从下到上
            for (int i = b; i >= t; i--) {
                ints[i][l] = num ++;
            }
            l ++;

        }
        return ints;
    }
}
