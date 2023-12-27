package leetcode2023;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author luna
 * @date 2023/12/27
 */
public class ContainsNearbyDuplicate_219 {

    /**
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,2,3,1], k = 3
     * 输出：true
     * 示例 2：
     * <p>
     * 输入：nums = [1,0,1,1], k = 1
     * 输出：true
     * 示例 3：
     * <p>
     * 输入：nums = [1,2,3,1,2,3], k = 2
     * 输出：false
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        int start = 0;
        int end = nums.length - 1;

        while (start < nums.length) {
            while (end > start) {
                if (nums[start] == nums[end] && Math.abs(start - end) <= k) {
                    return true;
                }
                end--;
            }
            start++;
            end = nums.length - 1;
        }

        return false;
    }

    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(num) && Math.abs(i - map.get(num)) <= k) {
                return true;
            }
            map.put(num, i);
        }

        return false;
    }


    public static boolean containsNearbyDuplicate3(int[] nums, int k) {

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (i > k){
                set.remove(nums[i - k - 1]);
            }
            if (set.contains(nums[i])){
                return true;
            }
            set.add(nums[i]);
        }

        return false;

    }

        public static void main(String[] args) {
        int[] nums = new int[]{1, 0, 1, 1};
        System.out.println(containsNearbyDuplicate(nums, 1));
    }
}
