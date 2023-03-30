package leetcode2023;

import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;

/**
 * @author chenzhangyue
 * 2023/3/29
 */
public class TwoSum_001 {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] ints = twoSum(nums, target);
        System.out.println(ints[0] + "," + ints[1]);
    }

    private static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            return new int[]{-1, -1};
        }
        HashMap<Integer/*数值*/, Integer/*位置下标*/> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                Integer index = map.get(complement);
                if (index != null) {
                    return new int[]{i, index};
                }
            }

            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }
}
