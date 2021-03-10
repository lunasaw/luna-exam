package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/8 23:50
 */
public class PalindromeNumber {

	/**
	 * 判断是否是数字回文
	 * @param x
	 * @return
	 */
	public static boolean isPalindrome(int x) {
		if (x < 0) {
			return false;
		}
		int div = 1;
		int num = x;
		while (x / div >= 10) {
			div *= 10;
		}
		while (num != 0) {
			int left = num / div;
			int right = num % 10;
			if (left != right) {
				return false;
			}
			num = (num - left * div) / 10;
			div /= 100;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isPalindrome(424));
	}

}
