package com.luna.practice.ls.daily;

import java.util.HashMap;

/**
 * @author Luna@win10
 * @date 2020/4/24 15:53
 */
public class Test_04_24_2 {

	/**
	 * 使用辅助空间(使用哈希表，时间复杂度是O(n),空间复杂度：O(n),n是数组大小)
	 *
	 * @param nums
	 * @param target
	 * @return 没有找到的话数组中数值就是{-1,-1}，否则找到
	 */
	public static int[] findTwo3(int[] nums, int target) {
		// 结果数组
		int[] result = {-1, -1};
		// 目标是数组下标，所以键值对为<数值,数值对应数组下标>，这里要说一下，
		//哈希表的查找的时间复杂度是O(1)
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		// 1.扫描一遍数组，加入哈希表，时间复杂度是O(n)
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}
		// 2.第二次扫描，目标值-当前值，差值作为key，看看map里有木有，没有就下一个循环，
		//直到数组扫描完毕或找到value,所以最坏情况的时间复杂度是O(n)
		for (int i = 0; i < nums.length; i++) {
			// 得到第二个数的值
			int two = target - nums[i];
			// 如果存在第二个数的数组下标&&结果的两个数不是同一个数的值
			if (map.containsKey(two) && target != 2 * two) {
				result[0] = i;
				result[1] = map.get(two);
				// 返回找到的两个数的数组下标
				return result;
			}
		}
		// 没有找到
		return result;
	}
}
