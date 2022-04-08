package preheat;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 23, 03, 2022
 * @Description: Merge K sorted linked lists
 *      https://leetcode.com/problems/merge-k-sorted-lists/
 * @Note:
 */
public class Code21_MergeKSortedLists {
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static class MyComparator implements Comparator<ListNode> {
        @Override
        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }

    /**
     * @param lists Head nodes of K sorted linked lists
     * @return Head node of the merged list
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }

        PriorityQueue<ListNode> heap = new PriorityQueue<>(new MyComparator());
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.add(lists[i]);
            }
        }
        if (heap.isEmpty()) {
            return null;
        }

        ListNode head = heap.poll();
        ;
        ListNode pre = head;
        if (pre.next != null) {
            heap.add(pre.next);
        }
        while (heap.size() > 0) {
            ListNode cur = heap.poll();
            pre.next = cur;
            pre = cur;
            if (pre.next != null) {
                heap.add(pre.next);
            }
        }
        return head;
    }
}
