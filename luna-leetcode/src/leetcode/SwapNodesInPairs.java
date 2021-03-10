package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/25 12:46
 */
public class SwapNodesInPairs {

    /**
     * 链表两两交换
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode l1 = dummy;
        ListNode l2 = head;
        while (l2 != null && l2.next != null) {
            ListNode nextStart = l2.next.next; // ->3
            l1.next=l2.next; // l1->2
            l2.next.next=l2; // 2->1
            l2.next=nextStart; // l2->3 1->3
            l1=l2; // l1->2
            l2=l1.next; // l2->3
        }
        return dummy.next;
    }

}
