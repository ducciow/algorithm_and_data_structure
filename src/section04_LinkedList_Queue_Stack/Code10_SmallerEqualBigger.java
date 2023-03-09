package section04_LinkedList_Queue_Stack;

import java.util.ArrayList;

import static java.util.Collections.swap;

/**
 * @Author: duccio
 * @Date: 07, 04, 2022
 * @Description: Partition a linked list according to a given value, ie., smaller, ..., equal, ..., bigger.
 * @Note:   Ver1. Extra space O(N):
 *                - Convert linked list to array list, partition, and convert back to linked list.
 *          Ver2. Extra space O(1):
 *                - Use 3*2 pointers, assign nodes to corresponding pointers, string them together.
 *          ======
 *          - Make sure the original node.next is clear, otherwise might result in infinite loops.
 */
public class Code10_SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            this.value = val;
        }
    }

    public static Node linkedListPartition1(Node head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }
        ArrayList<Node> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        arrPartition(arr, pivot);
        Node ret = arr.get(0);
        Node cur = ret;
        for (int i = 1; i < arr.size(); i++) {
            cur.next = arr.get(i);
            cur = cur.next;
        }
        cur.next = null;  // clear the last node.next
        return ret;
    }

    private static void arrPartition(ArrayList<Node> arr, int pivot) {
        int smaller = -1;
        int bigger = arr.size();
        for (int i = 0; i < bigger; i++) {
            if (arr.get(i).value < pivot) {
                swap(arr, ++smaller, i);
            } else if (arr.get(i).value > pivot) {
                swap(arr, --bigger, i--);
            }
        }
    }

    public static Node linkedListPartition2(Node node, int pivot) {
        if (node == null || node.next == null) {
            return node;
        }
        Node sHead = null;
        Node sTail = null;
        Node eHead = null;
        Node eTail = null;
        Node bHead = null;
        Node bTail = null;
        // assign nodes
        while (node != null) {
            if (node.value < pivot) {
                if (sHead == null) {
                    sHead = node;
                    sTail = node;
                } else {
                    sTail.next = node;
                    sTail = node;
                }
            } else if (node.value == pivot) {
                if (eHead == null) {
                    eHead = node;
                    eTail = node;
                } else {
                    eTail.next = node;
                    eTail = node;
                }
            } else {
                if (bHead == null) {
                    bHead = node;
                    bTail = node;
                } else {
                    bTail.next = node;
                    bTail = node;
                }
            }
            // clear the original node.next
            Node next = node.next;
            node.next = null;
            node = next;
        }
        // string them together
        Node head;
        if (sHead != null) {
            head = sHead;
            if (eHead != null) {
                sTail.next = eHead;
                eTail.next = bHead;
            } else {
                sTail.next = bHead;
            }
        } else if (eHead != null) {
            head = eHead;
            eTail.next = bHead;
        } else {
            head = bHead;
        }
        return head;

//        if (sHead != null) {
//            sTail.next = eHead;
//            eTail = eHead == null ? sTail : eTail;
//        }
//        if (eHead != null) {
//            eTail.next = bHead;
//        }
//        return sHead != null ? sHead : (eHead != null ? eHead : bHead);
    }


    public static Node generateRandLinkedList(int maxL, int maxV) {
        int N = (int) (Math.random() * (maxL + 1));
        if (N == 0) {
            return null;
        }
        Node head = new Node((int) (Math.random() * (maxV + 1)));
        Node cur = head;
        for (int i = 1; i < N; i++) {
            cur.next = new Node((int) (Math.random() * (maxV + 1)));
            cur = cur.next;
        }
        return head;
    }

    public static boolean check(Node head, int pivot) {
        if (head == null) {
            return true;
        }
        int lastSmall = 0;
        int lastEqual = 0;
        int lastBig = 0;
        int idx = -1;
        while (head != null) {
            idx++;
            if (head.value < pivot) {
                lastSmall = idx;
            } else if (head.value == pivot) {
                lastEqual = idx;
            } else {
                lastBig = idx;
            }
            head = head.next;
        }

        return lastSmall <= lastEqual && lastEqual <= lastBig
                || lastEqual == 0
                || lastBig == 0;
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
        int maxL = 10;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int pivot = (int) (Math.random() * (maxV + 1));
            Node head = generateRandLinkedList(maxL, maxV);
            head = linkedListPartition2(head, pivot);
            if (!check(head, pivot)) {
                System.out.println("Failed on case below with pivot " + pivot);
                printLinkedList(head);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
