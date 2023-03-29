package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/12 21:04
 */
public class Integer2Roman {
	public static String intToRoman(int num) {
		String[] args = new String[]{"M", "D", "C", "L", "X", "V", "I"};
		int[] nums = new int[]{1000, 500, 100, 50, 10, 5, 1};
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < nums.length; i++) {
			while (num >= nums[i]) {
				num -= nums[i];
				stringBuilder.append(args[i]);
			}
		}
		return stringBuilder.toString();
	}

	public String intToRomanTwo(int num) {
		String[][] strings =new String[][]{
				{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
				{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
				{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
				{"", "M", "MM", "MMM"}
		};
		StringBuilder roman=new StringBuilder();
		roman.append(strings[3][num / 1000]);
		roman.append(strings[2][num / 100 % 10]);
		roman.append(strings[1][num / 10 % 10]);
		roman.append(strings[0][num % 10]);

		return roman.toString();
	}

	public static void main(String[] args) {
		System.out.println(intToRoman(3));
	}
}
