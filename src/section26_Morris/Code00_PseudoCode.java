package section26_Morris;

/**
 * @Author: duccio
 * @Date: 11, 05, 2022
 * @Description: Pseudo code of Morris traversal.
 * @Note:   1. Time complexity O(N).
 *          2. Space complexity O(1).
 *          ======
 *          1. Those nodes with left child are traversed twice, otherwise are traversed once.
 *          2. In general for applications, further operations take place on the twice traversing.
 *          ======
 *
 *                                                  Morris      Binary Tree DP
 *          if require entire info from subtree:      no             yes
 */
public class Code00_PseudoCode {

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
