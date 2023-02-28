package section26_Morris;

/**
 * @Author: duccio
 * @Date: 12, 05, 2022
 * @Description: Implement Morris traversal, and modify it to pre-, in-, post-traversal. Also, use Morris for IsBST.
 * @Note:   1. Morris to Pre: print at the first time all nodes are visited.
 *          2. Morris to In: print when visit-once nodes are visited and visit-twice nodes are visited the second time.
 *          3. Morris to Post: only print when visit-twice nodes are visited the second time, which reversely print the
 *             right boundary of its left subtree. Besides, at the very end, reversely print the right boundary of the
 *             root.
 *          4. Morris to IsBST: check if previous node is larger than the current node before whenever the current goes
 *             to its right.
 */
public class Code01_MorrisTraversal {

    public static void main(String[] args) {
        Node root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(6);
        root.left.left = new Node(1);
        root.left.right = new Node(3);
        root.right.left = new Node(5);
        root.right.right = new Node(7);
        morris(root);
        morrisPre(root);
        morrisIn(root);
        morrisPost(root);
        System.out.println(isBST(root));
    }

    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }

    public static void morris(Node root) {
        if (root == null) {
            return;
        }
        Node cur = root;
        Node rightMost = null;
        while (cur != null) {
            System.out.print(cur.val + " ");
            rightMost = cur.left;
            if (rightMost != null) {
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightMost.right = null;
                }
            }
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisPre(Node root) {
        if (root == null) {
            return;
        }
        Node cur = root;
        Node rightMost = null;
        while (cur != null) {
            rightMost = cur.left;
            if (rightMost != null) {
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    System.out.print(cur.val + " ");
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightMost.right = null;
                }
            } else {
                System.out.print(cur.val + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisIn(Node root) {
        if (root == null) {
            return;
        }
        Node cur = root;
        Node rightMost = null;
        while (cur != null) {
            rightMost = cur.left;
            if (rightMost != null) {
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightMost.right = null;
                }
            }
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    private static void morrisPost(Node root) {
        if (root == null) {
            return;
        }
        Node cur = root;
        Node rightMost = null;
        while (cur != null) {
            rightMost = cur.left;
            if (rightMost != null) {
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightMost.right = null;
                    reversePrintRightBoundary(cur.left);
                }
            }
            cur = cur.right;
        }
        reversePrintRightBoundary(root);
        System.out.println();
    }

    private static void reversePrintRightBoundary(Node head) {
        Node tail = reverse(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverse(tail);
    }

    public static Node reverse(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    public static boolean isBST(Node root) {
        if (root == null) {
            return true;
        }
        Node cur = root;
        Node rightMost = null;
        Node pre = null;  // tracking previous node
        boolean ans = true;
        while (cur != null) {
            rightMost = cur.left;
            if (rightMost != null) {
                while (rightMost.right != null && rightMost.right != cur) {
                    rightMost = rightMost.right;
                }
                if (rightMost.right == null) {
                    rightMost.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    rightMost.right = null;
                }
            }
            // check if previous node is greater than the current node
            if (pre != null && pre.val > cur.val) {
                ans = false;
            }
            // set the previous node
            pre = cur;
            cur = cur.right;
        }
        return ans;
    }


}
