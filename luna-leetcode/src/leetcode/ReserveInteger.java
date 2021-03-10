package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/5 19:35
 */
public class ReserveInteger {

	public static int reverse(int x) {
		int rev = 0;
		while (x != 0) {
			int newrev = rev * 10 + x % 10;
			if ((newrev - x % 10) / 10 != rev) {
				return 0;
			}
			rev = newrev;
			x /= 10;
		}
		return rev;
	}

	public static void main(String[] args) {
		int reverse = reverse(11321312);
		System.out.println(reverse);
	}

}
