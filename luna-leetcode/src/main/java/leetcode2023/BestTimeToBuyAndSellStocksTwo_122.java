package leetcode2023;

import java.util.Arrays;

public class BestTimeToBuyAndSellStocksTwo_122 {

    static int[] prices;

    static int[][] memo;

    public static int maxProfitTwo(int[] prices) {
        BestTimeToBuyAndSellStocksTwo_122.prices = prices;

        memo = new int[prices.length][2];

        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], -1);
        }

        return dfs(prices.length - 1, 0);
    }

    public static int dfs(int i, int hold) {
        if (i < 0) {
            return hold == 1 ? Integer.MIN_VALUE : 0;
        }
        if (memo[i][hold] != -1) {
            return memo[i][hold];
        }

        if (hold == 1) {
            // 买入-钱 开始为0 结束为1 开始没有股票，结束持有股票
            return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 1, 0) - prices[i]);
        } else {
            // 卖出+钱 开始为1 结束为0 开始持有股票，结束没有股票
            return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i]);
        }
    }

    public static int maxProfit(int[] prices) {

        if (prices.length <= 1) {
            return 0;
        }
        int maxSub = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxSub += prices[i] - prices[i - 1];
            }
        }

        return maxSub;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{7, 1, 5, 3, 6, 4};
        int max = maxProfitTwo(nums);
        System.out.println(max);
    }
}
