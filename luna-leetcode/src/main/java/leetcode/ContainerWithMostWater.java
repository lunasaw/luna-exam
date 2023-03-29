package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/11 20:28
 */
public class ContainerWithMostWater {

	/**
	 * 装水
	 *
	 * @param height
	 * @return
	 */
	public static int maxArea(int[] height) {
			int res = 0;
			int l = 0, r = height.length - 1;
			while (l < r) {
				res = Math.max(res, Math.min(height[l], height[r]) * (r - l));
				if (height[r] > height[l]) {
					l++;
				} else {
					r--;
				}
			}
			return res;
	}

	public static void main(String[] args) {
		int[] height = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
		System.out.println(maxArea(height));;
	}

}
