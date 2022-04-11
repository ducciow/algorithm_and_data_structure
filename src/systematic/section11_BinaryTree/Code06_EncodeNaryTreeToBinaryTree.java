package systematic.section11_BinaryTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Given an N-ary tree, encode it to a binary tree, and then decode back to the original.
 *      https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree (locked)
 * @Note:   1. For any n-ary node, put its first child node to the left of its corresponding binary node, and put all
 *              following child nodes to the left's right, ie., all of its children forms the right boundary of the
 *              left subtree of its corresponding binary node.
 *          2. Recursively generate the right boundary of lest subtree from the children list when encoding.
 *          3. Recursively add child nodes to the children list from the left subtree when decoding.
 */
public class Code06_EncodeNaryTreeToBinaryTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }
    }


    public static class Codec {

        // encode an n-ary tree to a binary tree
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode bRoot = new TreeNode(root.val);
            bRoot.left = encodeProcess(root.children);
            return bRoot;
        }

        private TreeNode encodeProcess(List<Node> nChildren) {
            TreeNode root = null;
            TreeNode cur = null;
            for (Node nChild : nChildren) {
                TreeNode bNode = new TreeNode(nChild.val);
                if (root == null) {
                    root = bNode;
                } else {
                    cur.right = bNode;
                }
                cur = bNode;
                cur.left = encodeProcess(nChild.children);
            }
            return root;
        }
    }

    // decode back to the n-ary tree
    public Node decode(TreeNode bRoot) {
        if (bRoot == null) {
            return null;
        }
        Node root = new Node(bRoot.val);
        root.children = decodeProcess(bRoot.left);
        return root;
    }

    private List<Node> decodeProcess(TreeNode bRoot) {
        List<Node> children = new ArrayList<>();
        TreeNode cur = bRoot;
        while (cur != null) {
            Node child = new Node(cur.val);
            children.add(child);
            child.children = decodeProcess(cur);
            cur = cur.right;
        }
        return children;
    }

}
