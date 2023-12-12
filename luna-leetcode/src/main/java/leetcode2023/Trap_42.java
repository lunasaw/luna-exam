package leetcode2023;

/**
 * @author weidian
 * @version 1.0
 * @date 2023/12/12
 * @description:
 */
public class Trap_42 {

    public static int trap(int[] height) {
        if (height.length == 0) {
            return 0;
        }
        int total = 0;
        int[] leftMax = new int[height.length];
        leftMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[height.length];
        rightMax[height.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        for (int i = 0; i < height.length; i++) {
            total += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return total;
    }

    public static int trap2(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int maxLeft = height[0];
        int maxRight = height[height.length - 1];

        int total = 0;
        while (left < right) {
            maxLeft = Math.max(maxLeft, height[left]);
            maxRight = Math.max(maxRight, height[right]);

            if (height[left] < height[right]) {
                total += (maxLeft - height[left]);
                left++;
            } else {
                total += (maxRight - height[right]);
                right--;
            }
        }

        return total;
    }

    public static int trap3(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int maxLeft = height[0];
        int maxRight = height[n - 1];
        int total = 0;
        while (left < right){
            maxLeft = Math.max(maxLeft, height[left]);
            maxRight = Math.max(maxRight, height[right]);
            if (height[left] < height[right]){
                total += maxLeft - height[left];
                left ++;
            } else {
                total += maxRight - height[right];
                right ++;
            }
        }

        return total;
    }

        public static void main(String[] args) {
        int[] inde = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int[] nums = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap2(nums));
    }
}
