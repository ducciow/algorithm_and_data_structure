package systematic.section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: In a linked list, delete all the occurrences of a given value.
 * @Note:   1. Filter the head nodes with the given value to make the head node either has an other value or is null.
 *          2. Declare two variables: pre and cur initialized to be the head.
 *          3. Iterate until cur is null:
 *             - if cur.val is the value, fix pre and move pre.next forward.
 *             - else move pre forward.
 *             - move cur forward.
 *          4. return head.
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
