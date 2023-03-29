package leetcode;

/**
 * @author Luna@win10
 * @date 2020/5/21 14:41
 */
public class MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                cur.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 != null) {
            cur.next = l1;
        } else {
            cur.next = l2;
        }
        return dummy.next;
    }

    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1==null) {
            return l2;
        }
        if (l2==null){
            return l1;
        }
        if (l1.val<l2.val){
            l1.next=mergeTwoLists2(l1.next,l2);
            return l1;
        }else {
           l2.next= mergeTwoLists2(l1,l2.next);
            return l2;
        }
    }

}
