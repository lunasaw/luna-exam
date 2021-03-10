package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/19 20:52
 */
public class RemoveNthNodeFromEndOfList {

	/**
	 * 删除倒数第n个节点
	 * @param head
	 * @param n
	 * @return
	 */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode listNode = new ListNode(0);
        ListNode show = listNode;
        ListNode fast = listNode;
        listNode.next = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            show = show.next;
            fast = fast.next;
        }
        show.next=show.next.next;
        return listNode.next;
    }

    class ListNode {
        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

	public static void main(String[] args) {

	}

}
