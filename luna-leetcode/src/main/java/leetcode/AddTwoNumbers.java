package leetcode;

/**
 * @author Luna@win10
 * @date 2020/4/29 20:25
 */

class ListNode {

	int val;
	/** 下一个链表对象 */
	ListNode next;

	/** 赋值链表的值 */
	ListNode(int x) {
		val = x;
	}


}

public class AddTwoNumbers {

	/**
	 * 求两数的和
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummy = new ListNode(-1);
		ListNode cur = dummy;
		int carry = 0;
		while (l1 != null || l2 != null) {
			//位数上的是否为空 如为空则看作零
			int d1 = l1 == null ? 0 : l1.val;
			int d2 = l2 == null ? 0 : l2.val;
			//当前和
			int sum = d1 + d2 + carry;
			//进位 三目运算效率更高
			carry = sum >= 10 ? 1 : 0;
//			carry = sum / 10;
			//new 一个节点赋值给next 值为 sum 的摸
			cur.next = new ListNode(sum % 10);
			//链接节点
			cur = cur.next;
			if (l1 != null) {
				//位数移到下一位
				l1 = l1.next;
			}
			if (l2 != null) {
				l2 = l2.next;
			}
		}
		if (carry == 1) {
			//位数计算完后 如果carry==1 ==>最高位有进位
			cur.next = new ListNode(1);
		}
		return dummy.next;
	}
}


