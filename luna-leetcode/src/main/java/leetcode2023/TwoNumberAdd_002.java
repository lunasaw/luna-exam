package leetcode2023;

/**
 * @author chenzhangyue
 * 2023/3/30
 */
public class TwoNumberAdd_002 {

    /**
     * 需要逆序链表输出即可，每次求个位数，保留进位，每次求个加上进位数 / 10 计算新进位
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 解释：342 + 465 = 807
     */
    public static void main(String[] args) {
        ListNode l10 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        ListNode l12 = new ListNode(3);

        ListNode l20 = new ListNode(5);
        ListNode l21 = new ListNode(6);
        ListNode l22 = new ListNode(4);
        l10.next = l11;
        l11.next = l12;
        l20.next = l21;
        l21.next = l22;

        ListNode listNode = addTwoNumbers(l10, l20);
        while (listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int pre = 0;
        while (l1 != null || l2 != null) {
            int sum = pre + l1.val + l2.val;
            System.out.println(pre);
            pre = sum / 10;
            int curent = sum % 10;
            current.next = new ListNode(curent);
            current = current.next;

            l1 = l1.next;
            l2 = l2.next;
        }

        if (pre > 0){
            ListNode listNode = new ListNode(pre);
            current.next = listNode;
        }

        return dummyHead.next;
    }

    public static  class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
