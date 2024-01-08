package leetcode2024;

import lombok.Data;

import java.util.*;

/**
 * @author luna
 * @date 2024/1/8
 */
public class HasCycle_141 {

    /**
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * <p>
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，
     * 评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
     * <p>
     * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null){
            return false;
        }
        if (!list.add(head)) {
            return true;
        }
        return hasCycle(head.next);
    }

    public static boolean hasCycle3(ListNode head) {

        while (head != null){
            if (!list.add(head)){
                return true;
            }
            head = head.next;
        }

        return false;
    }

    private static final Set<ListNode> list = new HashSet<>();

    public static boolean hasCycle2(ListNode head) {
        Set<ListNode> seen = new HashSet<ListNode>();
        while (head != null) {
            if (!seen.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    public static void main(String[] args) {
        // 3,2,0,-4
        ListNode listNode = new ListNode(1);
//        ListNode listNode1 = new ListNode(2);
//        listNode.setNext(listNode1);
//        ListNode listNode2 = new ListNode(0);
//        listNode1.setNext(listNode2);
//        ListNode listNode3 = new ListNode(-4);
//        listNode2.setNext(listNode3);
//        listNode3.setNext(listNode1);
        System.out.println(hasCycle3(listNode));
    }

    @Data
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
