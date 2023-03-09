package section03_BitOperation;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: Given an integer, extract its rightmost digit that is 1.
 * @Note:   - Sign does not matter.
 */
public class Code03_TheRightmostOne {

    public static int theRightmostOne(int n) {
        return n & (~n + 1);  // ie, n & -n
    }

}
