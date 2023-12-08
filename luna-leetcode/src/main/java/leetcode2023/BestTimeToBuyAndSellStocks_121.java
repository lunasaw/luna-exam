package leetcode2023;

public class BestTimeToBuyAndSellStocks_121 {

    public static int maxProfit(int[] prices) {
        int min = prices[0];
        int index = 0;

        int subMax = 0;
        for (int j = 0; j < prices.length; j++) {
            min = prices[j];
            for (int i = j; i < prices.length; i++) {
                if ((min - prices[i]) < 0) {
                    if (subMax < Math.abs(min - prices[i])) {
                        subMax = Math.abs(min - prices[i]);
                        index = i;
                    }
                }
            }
        }
        return subMax;
    }

    public static int maxProfit2(int[] prices) {
        int min = Integer.MAX_VALUE;

        int maxSub = 0;
        for (int i = 0; i < prices.length; i++) {
            if (min > prices[i]) {
                min = prices[i];
            } else if (maxSub < prices[i] - min) {
                maxSub = prices[i] - min;
            }
        }

        return maxSub;
    }

    public static void main(String[] args) {
        int[] muns = new int[]{2, 4, 1};
        int profit = maxProfit(muns);
        System.out.println(profit);
    }
}
