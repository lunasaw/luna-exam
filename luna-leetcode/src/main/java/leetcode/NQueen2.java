package leetcode;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: Nqueen2
 * @Author: luna
 * @CreateTime: 2020/7/2 22:42
 * @Description:
 */
public class NQueen2 {

    /**
     * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
     * <p>
     * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     * <p>
     * 示例:
     * <p>
     * 输入: 4
     * 输出: [
     * [".Q..",  // 解法 1
     * "...Q",
     * "Q...",
     * "..Q."],
     * <p>
     * ["..Q.",  // 解法 2
     * "Q...",
     * "...Q",
     * ".Q.."]
     * ]
     * 解释: 4 皇后问题存在两个不同的解法。
     *  
     * <p>
     * 提示：
     * <p>
     * 皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一到七步，可进可退。（引用自 百度百科 - 皇后 ）
     *
     * @param n
     * @return
     */
    int n;
    int[] res; // 记录每种方案的皇后放置索引
    int count = 0; // 总方案数

    public int totalNQueens(int n) {
        this.n = n;
        this.res = new int[n];
        check(0); // 第0行开始放置
        return count;
    }

    // 放置第k行
    public void check(int k) {
        if (k == n) {
            count++;
            return;
        }
        for (int i = 0; i < n; i++) {
            res[k] = i;  // 将位置i 放入索引数组第k个位置
            if (!judge(k)) {
                check(k + 1); //不冲突的话，回溯放置下一行
            }
            //冲突的话试下一个位置
        }
    }

    //判断第k行的放置是否与之前位置冲突
    public boolean judge(int k) {
        for (int i = 0; i < k; i++) {
            if (res[k] == res[i] || Math.abs(k - i) == Math.abs(res[k] - res[i])) {
                return true;
            }
        }
        return false;
    }
}
