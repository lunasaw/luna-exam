package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/5/15 19:00
 */
public class ThreeNum2Sum {

	/**
	 * 三数和为0
	 * @param nums
	 * @return
	 */
    public List<List<Integer>> threeSu(int[] nums) {
	    List<List<Integer>> list = new ArrayList<>();
        if (nums.length < 3 || nums == null) {
            return list;
        }
        int i = 0;
        Arrays.sort(nums);

        while (i < nums.length - 2) {
            int base = nums[i];
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = base + nums[left] + nums[right];
                if (sum == 0) {
                    LinkedList<Integer> linkedList = new LinkedList<>();
                    linkedList.add(base);
                    linkedList.add(nums[left]);
                    linkedList.add(nums[right]);
                    list.add(linkedList);
                    left = moveRight(nums, left + 1);
                    right = moveLeft(nums, right - 1);
                } else if (sum > 0) {
                    right = moveLeft(nums, right - 1);
                } else {
                    left = moveRight(nums, left + 1);
                }
            }
            i = moveRight(nums, i + 1);
        }
        return list;
    }

    public int moveLeft(int nums[], int right) {
        while (right == nums.length - 1 || (right >= 0 && nums[right] == nums[right + 1])) {
            right--;
        }
        return right;
    }

    public int moveRight(int nums[], int left) {
        while (left == 0 || (left < nums.length && nums[left] == nums[left - 1])) {
            left++;
        }
        return left;
    }

    public static void main(String[] args) {
        ThreeNum2Sum threeNum2Sum = new ThreeNum2Sum();
        List<List<Integer>> lists = threeNum2Sum.threeSu(new int[] {-1, 0, 1, 2, -1, -4});
        System.out.println(lists);
    }

}
