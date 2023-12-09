package leetcode2023;

public class CanJumpMin_45 {

    public static int jump(int[] nums) {
        int size = 0;
        int index = 0;
        int end = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            index = Math.max(nums[i] + i, index);
            if (i == end) {
                // 如果当前到达了最高点，则重置最高点
                end = index;
                size++;
            }

        }

        return size;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 1, 1, 4};
        System.out.println(jump(nums));
    }
}
