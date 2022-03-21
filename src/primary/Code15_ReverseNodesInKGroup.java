package primary;

/**
 * @Author: duccio
 * @Date: 21, 03, 2022
 * @Description: Given the head of a linked list, reverse K nodes at a time, and return the modified list. K is a
 *      positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a
 *      multiple of K then left-out nodes, in the end, should remain as it is.
 *      Eg. {1, 2, 3, 4, 5}, K = 2 ---> {2, 1, 4, 3, 5}
 *      https://leetcode.com/problems/reverse-nodes-in-k-group/
 * @Note:   Line 71 must not be changed, because by explicitly assigning the variable, the working segment of the list
 *      is now isolated from the latter segment. Otherwise, two variables directing the same address will be causing
 *      unexpected and problematic changes.
 *
 */
public class Code15_ReverseNodesInKGroup {

    public static void main(String[] args) {
        ListNode n5 = new ListNode(5, null);
        ListNode n4 = new ListNode(4, n5);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

        ListNode cur = n1;
        while (cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }
        System.out.println();

        ListNode res = reverseKGroup(n1, 2);
        cur = res;
        while (cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }
    }

    // do not submit this class on above link
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int v, ListNode n) {
            val = v;
            next = n;
        }
    }

    /**
     * return the end node within a group, if the remaining nodes in the list are enough for K
     * otherwise, the end node will be null
     *
     * @param start the first node within this group
     * @param K     group size
     * @return the last node within this group
     */
    public static ListNode getKGroupEnd(ListNode start, int K) {
        ListNode end = start;
        while (--K > 0 && end != null) {
            end = end.next;
        }
        return end;
    }

    /**
     * reverse the elements within a single group
     *
     * @param start the first node within the group
     * @param end   the last node within the group
     */
    public static void reverse(ListNode start, ListNode end) {
        end = end.next;  // <---
        ListNode cur = start;
        ListNode pre = null;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    public static ListNode reverseKGroup(ListNode head, int K) {
        ListNode start = head;
        ListNode end = getKGroupEnd(start, K);
        if (end == null) {  // the list size is smaller than K, do nothing
            return head;
        }
        // the remaining elements is at least enough for the 1st group
        head = end;
        reverse(start, end);
        // continue from the maybe-exist 2nd group
        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKGroupEnd(start, K);
            if (end == null) {
                return head;
            }
            reverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

}
