package systematic.section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Reverse a uni-directional linked list and a bi-directional linked list.
 * @Note:   For both uni- and bi-directional linked lists:
 *          - Declare two variables: pre and next to be null.
 *          - Iterate until the head is null:
 *              * grab the next node.
 *              * redirect head.next.
 *              * reassign pre.
 *              * reassign head.
 *          - return pre.
 */
public class Code01_ReverseList {

    public static class Node {
        int value;
        Node next;

        public Node(int val) {
            value = val;
        }
    }

    public static class BiNode {
        int value;
        BiNode pre;
        BiNode next;

        public BiNode(int val) {
            value = val;
        }
    }

    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static BiNode reverseBiLinkedList(BiNode head) {
        BiNode pre = null;
        BiNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.pre = next;
            pre = head;
            head = next;
        }
        return pre;
    }

}
