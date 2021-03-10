package leetcode;

/**
 * @author luna@mac
 * @className leetcode.RotateRight.java
 * @description TODO
 * <p>
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 * @createTime 2021年01月17日 21:57:00
 */
public class RotateRight {

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }

        ListNode oldTail = head;

        int n = 0;
        // 总节点个数
        for (n = 1; oldTail.next != null; n++) {
            oldTail = oldTail.next;
        }

        // 形成环
        oldTail.next = head;

        ListNode newTail = head;

        // 找到新的尾节点
        for (int i = 0; i < n - k % n - 1; i++) {
            newTail = newTail.next;
        }

        // 尾节点的next 就是头节点
        ListNode newHead = newTail.next;

        // 断开
        newTail.next = null;

        // 返回头
        return newHead;
    }


    public class ListNode {
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
