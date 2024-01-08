package leetcode2024;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author luna
 * @date 2024/1/8
 */
public class LongestConsecutive_128 {

    /**
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * <p>
     * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [100,4,200,1,3,2]
     * 输出：4
     * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
     * 示例 2：
     * <p>
     * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
     * 输出：9
     *
     * @param nums
     * @return
     */
    public static int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i] - 1)) {
                map.put(nums[i], map.get(nums[i] - 1) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }

        int total = 0;
        for (Integer value : map.values()) {
            total = Math.max(value, total);
        }

        return total;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(nums));
    }
}
