package section26_Morris;

/**
 * @Author: duccio
 * @Date: 11, 05, 2022
 * @Description: Pseudocode of Morris traversal of a binary tree.
 * @Note:   - For null pointers of a binary tree itself, by redirecting them and then undo the redirection, to
 *            achieve the space complexity O(1).
 *          ======
 *          - Those nodes with left child are traversed twice, otherwise are traversed once.
 *          - In general for applications, specific operations take place sometime during the two traversals.
 *          ======
 *          - Unlike binary tree DP, it does not require entire info from subtree。
 */
public class Code00_Pseudocode {

    /*

    Assume it comes to the current node, cur:

    if (cur.left != null):
        rightMost = the rightmost node in the left subtree
        if (rightMost.right == null):
            rightMost.right = cur
            cur = cur.left
        else if (rightMost.right == cur):
            rightMost.right = null
            cur = cur.right
    else:
        cur = cur.right

    if (cur == null):
        stop iterating

     */

}
