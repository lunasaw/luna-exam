package leetcode;

/**
 * @author Luna@win10
 * @date 2020/6/2 22:36
 */
public class NextPermutation {

    /**
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     *
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * 必须原地修改，只允许使用额外常数空间。
     *
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     * 1 2 7 4 3 1
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        // 找到比第一个右边比左边大的  2
        int firstSmall = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                firstSmall = i;
                break;
            }
        }

        // 如果没有 就反转
        if (firstSmall == -1) {
            reverse(nums, 0, nums.length - 1);
            return;
        }

        // 如果有  找比他大的下一个 3
        int firstarge = -1;
        for (int i = nums.length - 1; i > firstSmall; i--) {
            if (nums[i] > nums[firstSmall]) {
                firstarge = i;
                break;
            }
        }

        // 将两个位置交换  1 3 7 4 2 1
        swap(nums, firstSmall, firstarge);

        // 将交换后的右边的反转 1 3 1 2 4 7
        reverse(nums, firstSmall + 1, nums.length - 1);

        return;
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        NextPermutation nextPermutation = new NextPermutation();
        nextPermutation.nextPermutation(new int[] {1, 3, 2});
    }
}
