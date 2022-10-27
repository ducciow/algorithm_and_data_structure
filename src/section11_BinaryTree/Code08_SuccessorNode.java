package section11_BinaryTree;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: In a binary tree, nodes has an extra parent pointer. Given a such node, return its successor node, which
 *      is defined as the next node in in-order traversal.
 * @Note:   - If the given node has right subtree, then the answer must be the leftmost node of the right subtree.
 *          - Otherwise, trace upwards along the parent pointer, until meet one parent node with its direct left child
 *            on the trace path, then this parent node is the answer.
 */
public class Code08_SuccessorNode {

    public static class Node {
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int v) {
            value = v;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            return getMostLeft(node.right);
        }
        Node parent = node.parent;
        while (parent != null && parent.left != node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    private static Node getMostLeft(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


    public static void main(String[] args) {
        Node root = new Node(6);
        root.parent = null;
        root.left = new Node(3);
        root.left.parent = root;
        root.left.left = new Node(1);
        root.left.left.parent = root.left;
        root.left.left.right = new Node(2);
        root.left.left.right.parent = root.left.left;
        root.left.right = new Node(4);
        root.left.right.parent = root.left;
        root.left.right.right = new Node(5);
        root.left.right.right.parent = root.left.right;
        root.right = new Node(9);
        root.right.parent = root;
        root.right.left = new Node(8);
        root.right.left.parent = root.right;
        root.right.left.left = new Node(7);
        root.right.left.left.parent = root.right.left;
        root.right.right = new Node(10);
        root.right.right.parent = root.right;

        Node test = root.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = root.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));
    }

}
