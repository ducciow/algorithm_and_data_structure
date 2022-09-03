package systematic.section11_BinaryTree;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Fold a piece of paper N times, print the direction of folding edges (up or down). Eg., when fold 2
 *      times, the top-down folding edges would be: down, down, up.
 * @Note:   - It can be seen as an in-order traversal of a complete binary tree of level N.
 *          - Conceptually, left node refers to the upper fold of current fold, and it must be in the direction of
 *            down, and opposite for the conceptual right node.
 */
public class Code09_PaperFolding {

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }

    public static void printAllFolds(int N) {
        process(1, N, true);
    }

    private static void process(int level, int N, boolean down) {
        if (level > N) {
            return;
        }
        process(level + 1, N, true);
        System.out.println(down ? "down" : "up");
        process(level + 1, N, false);
    }
}
