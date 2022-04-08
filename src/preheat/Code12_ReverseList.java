package preheat;

/**
 * @Author: duccio
 * @Date: 21, 03, 2022
 * @Description: Reverse a uni-directional linked list and a bi-directional linked list
 * @Note:
 */
public class Code12_ReverseList {

    public static void main(String[] args) {
        // no test implemented
    }

    // Node for uni-directional linked list
    public static class Node {
        int value;
        Node next;

        public Node(int val) {
            value = val;
        }
    }

    // Node for bi-directional linked list
    public static class BiNode {
        int value;
        BiNode next;
        BiNode pre;

        public BiNode(int val) {
            value = val;
        }
    }

    // reverse uni-directional linked lists
    public static Node reverse(Node head) {
        Node cur = head;
        Node pre = null;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // reverse bi-directional linked lists
    public static BiNode reverseBi(BiNode head) {
        BiNode cur = head;
        BiNode pre = null;
        BiNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            cur.pre = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

}
