package systematic.section03_BitManipulation;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: Given an integer, extract its rightmost digit that is 1
 * @Note:   Regardless of the sign
 */
public class Code03_TheRightmostOne {

    public static int theRightmostOne(int n) {
        return n & (~n + 1);  // ie, n & -n
    }

}
