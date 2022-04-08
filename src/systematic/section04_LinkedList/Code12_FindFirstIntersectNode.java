package systematic.section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 08, 04, 2022
 * @Description: Given two linked lists that might have loops. Return the first intersect node if they are intersected,
 *      otherwise return null. If the total length of two lists are N, time should be O(N), space should be O(1).
 * @Note:   1. Find the first loop node if there is a loop:
 *              Use fast-slow pointers twice. For the first time, keep running until fast meets slow. For the second
 *              time, continue slow, and restart fast with step size 1. When the fast meets slow again, that is the first
 *              loop node.
 *          2. Discuss based on different situations:
 *              a) Both lists have no loop:
 *                  Traverse from head1/2 to tail1/2, get length1/2. If tail1 != tail2, no intersect. Otherwise, forward
 *                  from the node at (longer_length - shorter_length) of the longer list, meanwhile forward from the
 *                  start node of the shorter list.
 *              b) Only one list has a loop:
 *                  No intersect at all.
 *              c) Both lists have a loop:
 *                  If loop_node1 == loop_node2:
 *                      Same operation with a), regarding loop node as the tail node.
 *                  Else:
 *                      Forward from loop_node1, if it meets loop_node2, return either loop_node1/2. Otherwise, no
 *                      intersect.
 *          ======
 *          If ignore the extra space requirement, a HashSet can be used to check the first repeated node for finding
 *              loop node and intersect node.
 */
public class Code12_FindFirstIntersectNode {

    public static void main(String[] args) {
        validate();
    }

    public static class Node {
        int val;
        Node next;

        public Node(int v) {
            val = v;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loopNode1 = getLoopNode(head1);
        Node loopNode2 = getLoopNode(head2);
        if (loopNode1 == null && loopNode2 == null) {
            return noLoop(head1, head2);
        } else if (loopNode1 == null || loopNode2 == null) {
            return null;
        } else {
            return bothLoop(head1, head2, loopNode1, loopNode2);
        }
    }

    public static Node getLoopNode(Node head) {
        if (head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head.next;
        while (fast.next != null && fast.next.next != null && fast != slow) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast.next == null || fast.next.next == null) {
            return null;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    private static Node noLoop(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        // cur1 != cur2 means they are not intersected
        if (cur1 != cur2) {
            return null;
        }
        // reassign cur1 to longer list and cur2 to shorter list
        cur1 = n >= 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        // first traverse from longer list for n steps
        n = Math.abs(n);
        while (n > 0) {
            cur1 = cur1.next;
            n--;
        }
        // then traverse both lists at the same time until meet the first intersect node
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    private static Node bothLoop(Node head1, Node head2, Node loopNode1, Node loopNode2) {
        // Two lists intersect before or at loop node
        if (loopNode1 == loopNode2) {
            Node cur1 = head1;
            Node cur2 = head2;
            int n = 0;
            while (cur1.next != loopNode1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2.next != loopNode2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n > 0) {
                cur1 = cur1.next;
                n--;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }
        // intersect lies in the loop, or no intersect at all
        Node cur = loopNode1.next;
        while (cur != loopNode2 && cur != loopNode1) {
            cur = cur.next;
        }
        return cur == loopNode2 ? loopNode1 : null;
    }

    public static void validate() {

        // list1 no loop: 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // list2 no loop: 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // go to list1 node 6

        System.out.println(getIntersectNode(head1, head2).val);

        // list1 loop: 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // loop node 4

        // list2 loop: 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // go to list1 node 2

        System.out.println(getIntersectNode(head1, head2).val);

        // list2 loop: 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // go to list1 loop node 6

        System.out.println(getIntersectNode(head1, head2).val);
    }
}
