package systematic.section04_LinkedList;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 07, 04, 2022
 * @Description: Given a linked list, return: 1. the middle item (odd size) or left middle item (even size);
 *                                            2. the middle item (odd size) or right middle item (even size);
 *                                            3. the item before the middle or the item before the left middle;
 *                                            4. the item before the middle or the item before the right middle;
 * @Note:   Two pointers: fast and slow
 *          ======
 *          1. Base cases that should return null.
 *          2. Increase fast and slow pointers respectively.
 *          3. Customize boundaries based on odd/even size.
 */
public class Code08_LinkedListMid {

    public static void main(String[] args) {
        validate();
    }

    public static class Node {
        int value;
        Node next;

        public Node(int val) {
            value = val;
        }
    }

    public static Node midOrLeftMid(Node head) {
        if (head == null) {
            return null;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static Node midOrRightMid(Node head) {
        if (head == null) {
            return null;
        }
        Node fast = head;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return fast.next == null ? slow : slow.next;
    }

    public static Node midOrLeftMidPre(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static Node midOrRightMidPre(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        Node fast = head.next.next;
        Node slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return fast.next == null ? slow : slow.next;
    }


    public static Node naive1(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node naive2(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node naive3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        int N = arr.size();
        return (N & 1) == 0 ? arr.get((N - 1) / 2 - 1) : arr.get(N / 2 - 1);
    }

    public static Node naive4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        int N = arr.size();
        return (N & 1) == 0 ? arr.get((N - 1) / 2) : arr.get(N / 2 - 1);
    }

    public static Node generateRandLinkedList(int maxL) {
        int N = (int) (Math.random() * (maxL + 1));
        if (N == 0) {
            return null;
        }
        Node head = new Node(0);
        Node cur = head;
        for (int i = 1; i < N; i++) {
            cur.next = new Node(0);
            cur = cur.next;
        }
        return head;
    }

    public static void validate() {
        int numTest = 100;
        int maxL = 200;
        for (int i = 0; i < numTest; i++) {
            Node head = generateRandLinkedList(maxL);
            Node ans1 = midOrLeftMid(head);
            Node ans2 = naive1(head);
            if (ans1 != ans2) {
                System.out.println("Failed on midOrLeftMid()");
                return;
            }
            ans1 = midOrRightMid(head);
            ans2 = naive2(head);
            if (ans1 != ans2) {
                System.out.println("Failed on midOrRightMid()");
                return;
            }
            ans1 = midOrLeftMidPre(head);
            ans2 = naive3(head);
            if (ans1 != ans2) {
                System.out.println("Failed on midOrLeftMidPre()");
                return;
            }
            ans1 = midOrRightMidPre(head);
            ans2 = naive4(head);
            if (ans1 != ans2) {
                System.out.println("Failed on midOrRightMidPre()");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
