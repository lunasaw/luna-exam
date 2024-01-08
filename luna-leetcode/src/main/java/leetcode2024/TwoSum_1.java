package leetcode2024;

import java.util.HashMap;

/**
 * @author luna
 * @date 2024/1/8
 */
public class TwoSum_1 {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int i1 = target - nums[i];
            if (map.containsKey(i1)) {
                return new int[]{map.get(i1), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }


}
