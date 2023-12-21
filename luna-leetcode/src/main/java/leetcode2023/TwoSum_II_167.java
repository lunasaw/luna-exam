package leetcode2023;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/20
 * @description:
 */
public class TwoSum_II_167 {

    public static int[] twoSum(int[] numbers, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        int[] nums = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            Integer i1 = target - numbers[i];
            if (map.containsKey(i1)) {
                nums[1] = i + 1;
                nums[0] = map.get(i1);
                return nums;
            } else {
                map.put(numbers[i], i + 1);
            }
        }
        return nums;

    }

    public static int[] twoSum2(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right){
            int sum = numbers[left] + numbers[right];
            if (target > sum){
                left++;
            } else if (target < sum){
                right--;
            } else {
                return new int[]{left+1, right+1};
            }
        }
        return new int[]{-1,-1};
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 17;
        int[] ints = twoSum2(nums, target);
        System.out.println(Arrays.toString(ints));
    }

}
