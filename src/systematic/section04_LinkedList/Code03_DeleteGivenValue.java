package systematic.section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: In a linked list, delete all the occurrences of a given value
 * @Note:   1. Find the first node that has the value different to the given value, use it as the returning head
 *          2. Keep checking each following node, maintaining previous and current nodes
 */
public class Code03_DeleteGivenValue {

    public static class Node {
        int value;
        Node next;

        public Node(int val) {
            value = val;
        }
    }

    public static Node delete(Node head, int target) {
        while (head != null && head.value == target) {
            head = head.next;
        }
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == target) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
