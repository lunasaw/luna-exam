package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/27 21:12
 */
public class ReverseNodesInkGroup {

    public static ListNode reverseKGroup(ListNode head, int k) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head;
        int count = 0;
        while (cur != null && count != k) {
            // 走K步
            cur = cur.next;
            count++;
        }
        if (count == k) {
            cur = reverseKGroup(cur, k);
            while (count-- > 0) {
                ListNode tmmp = head.next;
                head.next = cur;
                cur = head;
                head = tmmp;
            }
            head = cur;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        reverseKGroup(head,2);
    }
}
