package primary;

/**
 * @Author: duccio
 * @Date: 21, 03, 2022
 * @Description: Given the heads of two sorted linked lists, merge them in one sorted list.
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * @Note:
 */
public class Code17_MergeTwoSortedLinkedList {

    // do not submit
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode mergeTwoLists(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }
        ListNode ret = head1.val <= head2.val ? head1 : head2;
        ListNode cur1 = ret.next;
        ListNode cur2 = head1.val <= head2.val ? head2 : head1;
        ListNode pre = ret;
        while (cur1 != null && cur2 != null) {
            if (cur1.val <= cur2.val) {
                pre.next = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        pre.next = cur1 == null ? cur2 : cur1;
        return ret;
    }
}
