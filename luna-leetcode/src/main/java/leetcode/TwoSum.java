package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Luna@win10
 * @date 2020/4/25 23:06
 */
public class TwoSum {

	/**
	 * 一个数组中找目标数和的组合
	 * @param nums
	 * @param target
	 * @return
	 */
	public static int[] twoSum(int[] nums, int target) {
		int res[] = new int[]{-1, -1};
		if (nums == null || nums.length <= 1) {
			return res;
		}

		// 目标是数组下标，所以键值对为<第一个数,第二个数>
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int num = nums[i];
			int val = target - num;

			// 如果存在第二个数的数组下标
			if (map.containsKey(val)) {
				res[0] = i;
				//存在 取出值
				res[1] = map.get(val);
				return res;
			} else {
				// 不存在加入哈希表
				map.put(num, i);
			}
		}
		return res;

	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int i = scanner.nextInt();
		int ints1[]=new int[i];
		for (int i1 = 0; i1 < i; i1++) {
			ints1[i1]=scanner.nextInt();
		}
		for (int x: ints1) {
			System.out.print(x);
		}
		int[] nums = new int[]{5, 6, 1, 4, 7, 9, 8};
		int target = scanner.nextInt();
		int[] ints = twoSum(nums, target);
		System.out.println(ints[0] + "," + ints[1]);
	}
}