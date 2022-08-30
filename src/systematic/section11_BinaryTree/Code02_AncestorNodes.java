package systematic.section11_BinaryTree;

/**
 * @Author: duccio
 * @Date: 08, 04, 2022
 * @Description: Given pre-order and post-order traversal, for a node X, the intersection of left nodes to X in the
 *      pre-order traversal and right nodes to X in the post-order traversal is all X's ancestor nodes.
 * @Note:
 */
public class Code02_AncestorNodes {

    /**
     * Proof:
     *  1. Ancestor nodes must appear in the left of pre-order traversal, and in the right of post-order traversal.
     *     So, the intersection must have all ancestor nodes.
     *  2. Offspring nodes must appear in the right of pre-order traversal, and in the left of post-order traversal.
     *     So, the intersection must have no offspring nodes.
     *  3. Similarly, for left-sibling nodes and right-sibling nodes.
     */

}
