package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/20
 * @description:
 */
public class MaxArea_11 {

    public static int maxArea(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int total = 0;
        while (left < right) {
            left++;
            int hight = right - left;
            int line = Math.min(height[left], height[right]);
            int temp = line * hight;
            total = Math.max(total, temp);
            if (left == right) {
                right--;
                left = 0;
            }
        }
        return total;
    }

    public static int maxArea2(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int total = 0;
        while (left < right) {
            int hight = right - left;
            int line = Math.min(height[left], height[right]);
            int temp = line * hight;
            total = Math.max(total, temp);

            if (height[left] < height[right]){
                left++;
            } else {
                right--;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 4, 5, 18, 17, 6};
        int i = maxArea2(nums);
        System.out.println(i);
    }
}
