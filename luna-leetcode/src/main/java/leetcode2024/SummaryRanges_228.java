package leetcode2024;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna
 * @date 2024/1/2
 */
public class SummaryRanges_228 {

    /**
     * 给定一个  无重复元素 的 有序 整数数组 nums 。
     * <p>
     * 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，并且不存在属于某个范围但不属于 nums 的数字 x 。
     * <p>
     * 列表中的每个区间范围 [a,b] 应该按如下格式输出：
     * <p>
     * "a->b" ，如果 a != b
     * "a" ，如果 a == b
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [0,1,2,4,5,7]
     * 输出：["0->2","4->5","7"]
     * 解释：区间范围是：
     * [0,2] --> "0->2"
     * [4,5] --> "4->5"
     * [7,7] --> "7"
     * 示例 2：
     * <p>
     * 输入：nums = [0,2,3,4,6,8,9]
     * 输出：["0","2->4","6","8->9"]
     * 解释：区间范围是：
     * [0,0] --> "0"
     * [2,4] --> "2->4"
     * [6,6] --> "6"
     * [8,9] --> "8->9"
     *
     * @param nums
     * @return
     */
    public static List<String> summaryRanges(int[] nums) {
        if (nums.length == 0){
            return new ArrayList<>();
        }
        ArrayList<String> list = new ArrayList<>();
        int index = nums[0];
        int end = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                end = nums[i];
            } else if (Math.abs(nums[i] - nums[i - 1]) > 1) {
                if (index == end){
                    list.add(index + "");
                } else {
                    list.add(index + "->" + end);
                }

                index = nums[i];
                end = nums[i];
            }
        }

        if (index == end){
            list.add(index + "");
        } else {
            list.add(index + "->" + end);
        }
        return list;

    }
    public static void main (String[]args){
        int [] nums = new int[]{-2147483648,-2147483647,2147483647};
        System.out.println(summaryRanges(nums));
    }

}
