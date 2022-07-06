package systematic.section04_LinkedList;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 07, 04, 2022
 * @Description: Given a linked list, check if it is palindrome.
 * @Note:   Ver1. Extra space O(N):
 *                - Use a stack to store the whole linked list.
 *          Ver2. Extra space O(N/2):
 *                - Use fast-slow pointers to just store half of the linked list to a stack.
 *          Ver3. Extra space O(1):
 *                - Use fast-slow pointers to reverse half of the linked list. Then reverse back after comparision.
 *                - The later half is preferable to reverse, because its head is always pointed that will not be lost.
 */
public class Code09_IsPalindromeList {

    public static class Node {
        int value;
        Node next;

        public Node(int val) {
            value = val;
        }
    }

    public static boolean isPalindrome1(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = fast.next == null ? slow : slow.next;
        Node midOrRightMid = slow;
        Stack<Node> stack = new Stack<>();
        while (slow != null) {
            stack.push(slow);
            slow = slow.next;
        }
        while (head != midOrRightMid) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = fast.next == null ? slow : slow.next;
        Node midOrRightMid = slow;
        // reverse list from (right)mid to end
        Node pre = null;
        Node next = null;
        while (slow != null) {
            next = slow.next;
            slow.next = pre;
            pre = slow;
            slow = next;
        }
        Node reverseHead = pre;
        Node cur1 = head;
        Node cur2 = reverseHead;
        boolean ans = true;
        while (cur1 != midOrRightMid) {
            if (cur1.value != cur2.value) {
                ans = false;
                break;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        // reverse back
        pre = null;
        while (reverseHead != null) {
            next = reverseHead.next;
            reverseHead.next = pre;
            pre = reverseHead;
            reverseHead = next;
        }
        return ans;
    }


    public static Node randGenPalindromeLinkedList(int maxL, int maxV) {
        int N = (int) (Math.random() * (maxL + 1));
        if (N == 0) {
            return null;
        }
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxV + 1));
        }
        // non-palindrome
        if (Math.random() < 0.5) {
            Node head = new Node(arr[0]);
            Node cur = head;
            for (int i = 1; i < N; i++) {
                cur.next = new Node(arr[i]);
                cur = cur.next;
            }
            return head;
        }
        // palindrome
        int[] arrRev = new int[N];
        int i = 0;
        for (int j = N - 1; j >= 0; j--) {
            arrRev[j] = arr[i++];
        }
        Node head = new Node(arr[0]);
        Node cur = head;
        for (int j = 1; j < N; j++) {
            cur.next = new Node(arr[j]);
            cur = cur.next;
        }
        for (int j = 0; j < N; j++) {
            if (j == 0 && Math.random() < 0.5) {
                continue;
            }
            cur.next = new Node(arrRev[j]);
            cur = cur.next;
        }
        return head;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 100;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            Node head = randGenPalindromeLinkedList(maxL, maxV);
            boolean ans1 = isPalindrome1(head);
            boolean ans2 = isPalindrome2(head);
            boolean ans3 = isPalindrome3(head);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Failed with answers " + ans1 + " " + ans2 + " " + ans3);
                printLinkedList(head);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
