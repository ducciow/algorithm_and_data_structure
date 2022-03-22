package primary.class01;

/**
 * @Author: duccio
 * @Date: 16, 03, 2022
 * @Description: Print the binary presentation of integers (32-digit).
 * @Note:   1. Integer in JAVA is signed: [-2**31, 2**31 - 1]
 *          2. The highest digit is for sign, the rest digits are for value
 *          3. Value of negative integers: flip and +1
 *          ======
 *          4. "<<": add 0 in the rightmost
 *          5. ">>": add 1/0 in the leftmost according to the original sign
 *          6. ">>>": add 0 in the leftmost
 *          ======
 *          7. -n == ~n + 1
 *          8. Integer.MIN_VALUE and 0: negating remains the same
 */
public class Code00_PrintBinary {

    public static void main(String[] args) {
//        int num = 1;
//        printBinary(num);
//        printBinary(num << 1);
//        printBinary(num << 2);
//        printBinary(num << 3);
    }

    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & 1 << i) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}
