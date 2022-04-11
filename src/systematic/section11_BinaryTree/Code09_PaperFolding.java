package systematic.section11_BinaryTree;

/**
 * @Author: duccio
 * @Date: 11, 04, 2022
 * @Description: Fold a piece of paper N times, print the direction of folding edges (up or down). Eg., when fold 2
 *      times, the top-down folding edges would be: down, down, up.
 * @Note:   1. It can be seen as an in-order traversal of a binary tree, whereas there is no binary tree in reality.
 *          2. Conceptually, left node refers to the upper fold of current fold, and it must be in the direction of down.
 *          3. Opposite for the conceptual right nodes.
 */
public class Code09_PaperFolding {

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }

    public static void printAllFolds(int N) {
        process(1, N, true);
    }

    private static void process(int curFold, int N, boolean down) {
        if (curFold > N) {
            return;
        }
        process(curFold + 1, N, true);
        System.out.println(down ? "down" : "up");
        process(curFold + 1, N, false);
    }
}
