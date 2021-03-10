package leetcode;

import java.util.Arrays;

/**
 * @author Luna@win10
 * @date 2020/5/16 21:38
 */
public class ThreeSumClosest {

	/**
	 * 三数求和绝对值最接近
	 *
	 * @param nums
	 * @param target
	 * @return
	 */
    public static int threeSumClosest(int[] nums, int target) {
        int res = nums[0] + nums[1] + nums[nums.length - 1];
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum > target) {
                    end--;
                } else {
                    start++;
                }
                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
            }
        }
        return res;
    }

    public int threeSumClosest2(int[] nums, int target) {
        Arrays.sort(nums);
        int result = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left != right) {
                int min = nums[i] + nums[left] + nums[left + 1];
                if (target < min) {
                    if (Math.abs(result - target) > Math.abs(min - target))
                        result = min;
                    break;
                }
                int max = nums[i] + nums[right] + nums[right - 1];
                if (target > max) {
                    if (Math.abs(result - target) > Math.abs(max - target))
                        result = max;
                    break;
                }
                int sum = nums[i] + nums[left] + nums[right];
                // 判断三数之和是否等于target
                if (sum == target)
                    return sum;
                if (Math.abs(sum - target) < Math.abs(result - target))
                    result = sum;
                if (sum > target) {
                    right--;
                    while (left != right && nums[right] == nums[right + 1])
                        right--;
                } else {
                    left++;
                    while (left != right && nums[left] == nums[left - 1])
                        left++;
                }
            }
            while (i < nums.length - 2 && nums[i] == nums[i + 1])
                i++;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[] {-1, 2, 1, -4}, 1));;
    }
}
