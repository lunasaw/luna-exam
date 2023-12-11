package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/11
 * @description:
 */
public class Candy_135 {

    public static int candy(int[] ratings) {
        if (ratings.length == 0) {
            return 0;
        }
        int all = 0;
        int[] candy = new int[ratings.length];
        candy[0] = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candy[i] = candy[i - 1] + 1;
            }
        }

        //从右向左遍历，如果左边孩子评分大于右边孩子，则左边孩子个数为max(左边孩子现在的糖果个数，右边孩子糖果个数加1)
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candy[i] = Math.max(candy[i], candy[i + 1] + 1);
            }

            // 记录[0,ratious.length-2]孩子的总糖果个数
            all += candy[i];
        }

        //因为每个孩子初始时都有1个糖果，所以还要加上初始总糖果数和最右边孩子的糖果数
        return all + candy[candy.length - 1] + candy.length;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4};
        int candy = candy(nums);
        System.out.println(candy);
    }
}
