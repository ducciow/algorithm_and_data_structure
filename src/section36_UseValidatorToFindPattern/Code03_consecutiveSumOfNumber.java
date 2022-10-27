package section36_UseValidatorToFindPattern;

/**
 * @Author: duccio
 * @Date: 25, 05, 2022
 * @Description: Given an integer, return whether it is the sum of consecutive smaller integers.
 * @Note:   Two ways of checking if an integer is the power of 2:
 *              1. num == (num & (~num + 1))
 *              2. (num & (num - 1)) == 0;
 */
public class Code03_consecutiveSumOfNumber {

    public static boolean naive(int num) {
        for (int start = 1; start < num; start++) {
            int sum = start;
            for (int next = start + 1; next < num; next++) {
                if (sum + next > num) {
                    break;
                }
                if (sum + next == num) {
                    return true;
                }
                sum += next;
            }
        }
        return false;
    }

    public static boolean pattern(int num) {
//        return num != (num & (~num + 1));

//        return num != (num & (-num));

        return (num & (num - 1)) != 0;
    }

    public static void main(String[] args) {
//        for (int num = 1; num < 200; num++) {
//            System.out.println(num + " : " + naive(num));
//        }

        for (int num = 1; num < 200; num++) {
            if (naive(num) != pattern(num)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
