package section36_UseValidatorToFindPattern;

/**
 * @Author: duccio
 * @Date: 25, 05, 2022
 * @Description: There are two types of bags with capacity 6 and 8. One wants to use these bags to backpack n apples,
 *      requiring all bags must be fully packed. Return the minimum number of bags used. If it cannot fulfill the
 *      requirement, return -1.
 * @Note:   1. Get a naive function.
 *          2. Use the output of validator to find the pattern.
 *          3. Write functions according to the pattern.
 */
public class Code01_AppleMinBags {

    public static int naive(int n) {
        if (n < 0) {
            return -1;
        }
        int rest = n;
        int bag8 = n / 8;
        while (bag8 >= 0) {
            rest = n - bag8 * 8;
            if (rest % 6 == 0) {
                return bag8 + rest / 6;
            } else {
                bag8--;
            }
        }
        return -1;
    }

    public static int pattern(int n) {
        if (n < 0 || (n & 1) != 0) {
            return -1;
        }
        if (n <= 16) {
            return n == 6 || n == 8 ? 1 : (n == 12 || n == 14 || n == 16 ? 2 : -1);
        } else {
            return n % 8 == 0 ? n / 8 : n / 8 + 1;
        }
    }


    public static void main(String[] args) {
//        for (int n = 1; n < 200; n++) {
//            System.out.println(n + ": " + naive(n));
//        }

        for (int n = 1; n < 10000; n++) {
            if (naive(n) != pattern(n)) {
                System.out.println("Failed for n = " + n);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
