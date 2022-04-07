package systematic.section04_LinkedList;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 07, 04, 2022
 * @Description: There is a special Node type that has a random pointer. Copy a given linked list of this node type.
 * @Note:   Ver1. Extra space O(N): Use a HashMap to store copied nodes, and assign next and rand pointers following
 *                                  the original linked list.
 *          Ver2. Extra space O(1): Insert copied nodes immediately after the original nodes, assign rand pointers,
 *                                  and finally split copied nodes from original nodes.
 */
public class Code11_CopyListWithRand {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.rand = head.next.next;
        head.next.rand = head;
        head.next.next.rand = head.next.next;

        Node cur = head;
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }

        System.out.println("======");
        Node copy = copyRandList2(head);
        cur = copy;
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }

    }

    public static class Node {
        int val;
        Node next;
        Node rand;

        public Node(int v) {
            val = v;
        }

        @Override
        public String toString() {
            String nextV = next != null ? String.valueOf(next.val) : " ";
            String randV = rand != null ? String.valueOf(rand.val) : " ";
            return "Node " + String.valueOf(val) + " next to " + nextV + " random to " + randV;
        }
    }

    public static Node copyRandList1(Node head) {
        if (head == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    public static Node copyRandList2(Node head) {
        if (head == null) {
            return null;
        }
        // insert copy node after original node
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }
        // assign rand pointers
        cur = head;
        while (cur != null) {
            cur.next.rand = cur.rand != null ? cur.rand.next : null;
            cur = cur.next.next;
        }
        // split copy and original
        Node ret = head.next;
        Node cur1 = head;
        Node cur2 = ret;
        while (cur1 != null) {
            cur1.next = cur1.next.next;
            cur2.next = cur2.next != null ? cur2.next.next : null;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return ret;
    }

}
