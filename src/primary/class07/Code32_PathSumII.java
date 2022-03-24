package primary.class07;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum
 *      of the node values in the path equals targetSum. Each path should be returned as a list of the node values,
 *      not node references.
 *      https://leetcode.com/problems/path-sum-ii
 * @Note:   1. Add a copy pf path into result
 *          2. Remove self before returning and end
 */
public class Code32_PathSumII {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static List<List<Integer>> res;

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        process(root, targetSum, 0, new ArrayList<>());
        return res;
    }

    public static void process(TreeNode root, int target, int curSum, List<Integer> path) {
        if (root.left == null && root.right == null) {
            if (root.val + curSum == target) {
                path.add(root.val);
                res.add(copy(path));
                path.remove(path.size() - 1);
            }
            return;
        }
        path.add(root.val);
        if (root.left != null) {
            process(root.left, target, curSum + root.val, path);
        }
        if (root.right != null) {
            process(root.right, target, curSum + root.val, path);
        }
        path.remove(path.size() - 1);
    }

    private static List<Integer> copy(List<Integer> l) {
        List<Integer> out = new ArrayList<>();
        out.addAll(l);
        return out;
    }

}
