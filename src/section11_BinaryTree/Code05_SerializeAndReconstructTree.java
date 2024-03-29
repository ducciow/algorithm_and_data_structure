package section11_BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Given a binary tree, serialize it in forms of list of strings, and then reconstruct it from this list
 *      of strings, requiring the tree structure and list of strings are one-to-one.
 * @Note:   - For recursive order (pre and post):
 *              a) Need a queue for storing strings, and a helper function for recursive processing to the queue.
 *              b) For serialization, add value to a queue when process the current node. When meet null node, add null.
 *              c) For reconstruction, from pre-order is similar to serialize, while from post-order needs to initiate
 *                 a stack and poll all elements from queue to stack beforehand.
 *          - In-order traversal cannot serialize a BT, because it is not one-to-one.
 *          - For Level-order:
 *              a) No need for a helper function.
 *              b) Both serialization and reconstruction use an extra queue for BFS.
 *              c) Attention to null.
 */
public class Code05_SerializeAndReconstructTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            this.value = v;
        }
    }

    // pre serialization
    public static Queue<String> preSerial(Node root) {
        Queue<String> queue = new LinkedList<>();
        preProcess(root, queue);
        return queue;
    }

    private static void preProcess(Node root, Queue<String> queue) {
        if (root == null) {
            queue.add(null);
        } else {
            queue.add(String.valueOf(root.value));
            preProcess(root.left, queue);
            preProcess(root.right, queue);
        }
    }

    // pre reconstruction
    public static Node buildByPreQueue(Queue<String> queue) {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        return buildPreProcess(queue);
    }

    public static Node buildPreProcess(Queue<String> queue) {
        String cur = queue.poll();
        if (cur == null) {
            return null;
        }
        Node root = new Node(Integer.valueOf(cur));
        root.left = buildByPreQueue(queue);
        root.right = buildByPreQueue(queue);
        return root;
    }

    // post serialization
    public static Queue<String> postSerial(Node root) {
        Queue<String> queue = new LinkedList<>();
        postProcess(root, queue);
        return queue;
    }

    private static void postProcess(Node root, Queue<String> queue) {
        if (root == null) {
            queue.add(null);
        } else {
            postProcess(root.left, queue);
            postProcess(root.right, queue);
            queue.add(String.valueOf(root.value));
        }
    }

    // post reconstruction
    public static Node buildByPostQueue(Queue<String> queue) {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
        return buildPostProcess(stack);
    }

    private static Node buildPostProcess(Stack<String> stack) {
        String cur = stack.pop();
        if (cur == null) {
            return null;
        }
        Node root = new Node(Integer.valueOf(cur));
        root.right = buildPostProcess(stack);
        root.left = buildPostProcess(stack);
        return root;
    }

    // level serialization
    public static Queue<String> levelSerial(Node root) {
        Queue<String> queue = new LinkedList<>();
        if (root == null) {
            queue.add(null);
        } else {
            Queue<Node> tmp = new LinkedList<>();
            tmp.add(root);
            while (!tmp.isEmpty()) {
                Node cur = tmp.poll();
                if (cur == null) {
                    queue.add(null);
                } else {
                    queue.add(String.valueOf(cur.value));
                    tmp.add(cur.left);
                    tmp.add(cur.right);
                }
            }
        }
        return queue;
    }

    // level reconstruction
    public static Node buildByLevelQueue(Queue<String> queue) {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        Queue<Node> tmp = new LinkedList<>();
        Node root = generateNode(queue.poll());
        if (root != null) {
            tmp.add(root);
        }
        while (!tmp.isEmpty()) {
            Node cur = tmp.poll();
            cur.left = generateNode(queue.poll());
            cur.right = generateNode(queue.poll());
            if (cur.left != null) {
                tmp.add(cur.left);
            }
            if (cur.right != null) {
                tmp.add(cur.right);
            }
        }
        return root;
    }

    public static Node generateNode(String str) {
        if (str == null) {
            return null;
        }
        return new Node(Integer.valueOf(str));
    }


    public static Node genRandBT(int maxLevel, int maxValue) {
        return genRandBTProcess(maxLevel, maxValue, 1);
    }

    private static Node genRandBTProcess(int maxLevel, int maxValue, int curLevel) {
        if (curLevel > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node node = new Node((int) (Math.random() * (maxValue + 1)));
        node.left = genRandBTProcess(maxLevel, maxValue, curLevel + 1);
        node.right = genRandBTProcess(maxLevel, maxValue, curLevel + 1);
        return node;
    }

    public static boolean isSameStructure(Node root1, Node root2) {
        if (root1 == null ^ root2 == null) {
            return false;
        }
        if (root1 == null) {
            return true;
        }
        if (root1.value != root2.value) {
            return false;
        }
        return isSameStructure(root1.left, root2.left) && isSameStructure(root1.right, root2.right);
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 5;
        int maxV = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            Node root = genRandBT(maxL, maxV);
            Queue<String> pre = preSerial(root);
            Queue<String> post = postSerial(root);
            Queue<String> level = levelSerial(root);
            Node preBuild = buildByPreQueue(pre);
            Node postBuild = buildByPostQueue(post);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameStructure(preBuild, postBuild) || !isSameStructure(preBuild, levelBuild)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
