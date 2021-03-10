package com.luna.practice.ls;

public class ZeroOneKnapSack {

    /**
     * @param w  背包总重量
     * @param wt 物品重量数组
     * @param vt 物品价值数组
     * @param n  物品数量
     * @return int
     * @throws
     * @Title: knapSackRecursion
     * @Description: 递归求解0-1 规划问题  , 返回前n个物品在容量为w，得到的最大价值
     */
    public static int knapSackRecursion(int w, int[] wt, int[] vt, int n) {
        //边界条件判断
        if (n == 0 || w == 0)
            return 0;
        //d当前n个物品超重时
        if (wt[n - 1] > w)
            return knapSackRecursion(w, wt, vt, n - 1);
        else
            return Math.max(vt[n - 1] + knapSackRecursion(w - wt[n - 1], wt, vt, n - 1), knapSackRecursion(w, wt, vt, n - 1));
        //返回如下两种情况较大值（1）包含第n个物品（2）不包含第n个物品
    }

    /**
     * @param w  背包总重量
     * @param wt 物品重量数组
     * @param vt 物品价值数组
     * @param n  物品数量
     * @return
     */
    public static int knapSack(int w, int[] wt, int[] vt, int n) {
        //创建状态转移矩阵
        int[][] tc = new int[n + 1][w + 1];

        //构建状态转移矩阵
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= w; j++) {
                //边界条件
                if (i == 0 || j == 0) {
                    tc[i][j] = 0;
                } else if (wt[i - 1] <= j)
                    tc[i][j] = Math.max(vt[i - 1] + tc[i - 1][j - wt[i - 1]], tc[i - 1][j]);
                else
                    tc[i][j] = tc[i - 1][j];
            }
        }
        return tc[n][w];
    }


    /**
     * 第二类背包：完全背包
     * 思路分析：
     * 01背包问题是在前一个子问题（i-1种物品）的基础上来解决当前问题（i种物品），
     * 向i-1种物品时的背包添加第i种物品；而完全背包问题是在解决当前问题（i种物品）
     * 向i种物品时的背包添加第i种物品。
     * 推公式计算时，f[i][y] = max{f[i-1][y], (f[i][y-weight[i]]+value[i])}，
     * 注意这里当考虑放入一个物品 i 时应当考虑还可能继续放入 i，
     * 因此这里是f[i][y-weight[i]]+value[i], 而不是f[i-1][y-weight[i]]+value[i]。
     *
     * @param V
     * @param N
     * @param weight
     * @param value
     * @return
     */
    public static int completePack(int V, int[] weight, int[] value, int N) {
        //初始化动态规划数组
        int[][] dp = new int[N + 1][V + 1];
        //为了便于理解,将dp[i][0]和dp[0][j]均置为0，从1开始计算
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < V + 1; j++) {
                //如果第i件物品的重量大于背包容量j,则不装入背包
                //由于weight和value数组下标都是从0开始,故注意第i个物品的重量为weight[i-1],价值为value[i-1]
                if (weight[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weight[i - 1]] + value[i - 1]);
            }
        }
        //则容量为V的背包能够装入物品的最大值为
        int maxValue = dp[N][V];
        int j = V;
        StringBuilder numStr = new StringBuilder("");
        int sumValue = 0;
        for (int i = N; i > 0; i--) {
            //若果dp[i][j]>dp[i-1][j],这说明第i件物品是放入背包的
            while (dp[i][j] > dp[i - 1][j]) {
                numStr.append(i + " ");
                sumValue = sumValue + i * value[i];
                j = j - weight[i - 1];
            }
            if (j == 0)
                break;
        }
        System.out.println("装入背包的物品为: " + numStr.toString());
        return sumValue;
    }

    /**
     * 完全背包的第二种解法
     * 思路：
     * 只用一个一维数组记录状态，dp[i]表示容量为i的背包所能装入物品的最大价值
     * 用顺序来实现
     */
    public static int completePack2(int V, int N, int[] weight, int[] value) {

        //动态规划
        int[] dp = new int[V + 1];
        for (int i = 1; i < N + 1; i++) {
            //顺序实现
            for (int j = weight[i - 1]; j < V + 1; j++) {
                dp[j] = Math.max(dp[j - weight[i - 1]] + value[i - 1], dp[j]);
            }
        }
        return dp[V];
    }

    /**
     * 第三类背包：多重背包
     *
     * @param V
     * @param N
     * @param weight
     * @param value
     * @param num
     * @return
     */
    public static int manyPack(int V, int[] weight, int[] value, int N, int[] num) {
        //初始化动态规划数组
        int[][] dp = new int[N + 1][V + 1];
        //为了便于理解,将dp[i][0]和dp[0][j]均置为0，从1开始计算
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < V + 1; j++) {
                //如果第i件物品的重量大于背包容量j,则不装入背包
                //由于weight和value数组下标都是从0开始,故注意第i个物品的重量为weight[i-1],价值为value[i-1]
                if (weight[i - 1] > j)
                    dp[i][j] = dp[i - 1][j];
                else {
                    //考虑物品的件数限制
                    int maxV = Math.min(num[i - 1], j / weight[i - 1]);
					/*for(int k=0;k<maxV+1;k++){
						dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-k*weight[i-1]]+k*value[i-1]);
					}*/
                    for (int k = 0; k < maxV + 1; k++) {
                        dp[i][j] = dp[i][j] > Math.max(dp[i - 1][j], dp[i - 1][j - k * weight[i - 1]] + k * value[i - 1]) ? dp[i][j] : Math.max(dp[i - 1][j], dp[i - 1][j - k * weight[i - 1]] + k * value[i - 1]);
                    }
                }
            }
        }
        /*//则容量为V的背包能够装入物品的最大值为
         */
        int maxValue = dp[N][V];
        int j = V;
        StringBuilder numStr = new StringBuilder(" ");
        for (int i = N; i > 0; i--) {
            //若果dp[i][j]>dp[i-1][j],这说明第i件物品是放入背包的
            while (dp[i][j] > dp[i - 1][j]) {
                numStr.append(" " + i);
                j = j - weight[i - 1];
            }
            if (j == 0)
                break;
        }
        System.out.println("装入背包的物品为: " + numStr.toString());
        return dp[N][V];
    }


    /**
     * @param args
     * @return void
     * @throws
     * @Title: main
     * @Description: TODO
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        int[] vt = {1, 1, 2, 2, 3, 3, 4, 5};
        int[] num = {1, 5, 4, 5, 4, 3, 2, 1};
        int[] wt = {8, 10, 13, 15, 20, 24, 28, 33};
        int w = 100;
        int n = vt.length;
        System.out.println("利用动态规划求解01背包问题最大价值为：" + knapSack(w, wt, vt, n));

        System.out.println("完全背包且价值总和最大：" + completePack(w, wt, vt, n));

        System.out.println("多重背包价值总和最大为：" + manyPack(w, wt, vt, n, num));
    }

}