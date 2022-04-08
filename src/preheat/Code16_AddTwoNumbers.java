package preheat;

/**
 * @Author: duccio
 * @Date: 21, 03, 2022
 * @Description: Given two non-empty linked lists representing two non-negative integers, where the digits are stored
 *      in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum
 *      as a linked list. Eg. {2, 4, 3} and {5, 6, 4} ---> {7, 0, 8}, meaning 342 + 465 = 807.
 *      https://leetcode.com/problems/add-two-numbers/
 * @Note:
 */
public class Code16_AddTwoNumbers {

    // do not submit
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static int getLen(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    public static ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        int len1 = getLen(head1);
        int len2 = getLen(head2);
        ListNode headL = len1 > len2 ? head1 : head2;
        ListNode headS = len1 > len2 ? head2 : head1;
        ListNode ret = headL;  // modify long list for return
        ListNode lastNode = headL;  // track the last node for adding the last carry digit if needed
        int sum = 0;
        int carry = 0;
        // stage 1. both L and S have items
        while (headS != null) {
            sum = headL.val + headS.val + carry;
            headL.val = sum % 10;
            carry = sum / 10;
            lastNode = headL;
            headL = headL.next;
            headS = headS.next;
        }
        // stage 2. L has items left
        while (headL != null) {
            sum = headL.val + carry;
            headL.val = sum % 10;
            carry = sum / 10;
            lastNode = headL;
            headL = headL.next;
        }
        // stage 3. carry is 1
        if (carry == 1) {
            lastNode.next = new ListNode(1, null);
        }
        return ret;
    }
}
