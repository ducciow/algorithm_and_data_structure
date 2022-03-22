package primary.class05;

/**
 * @Author: duccio
 * @Date: 22, 03, 2022
 * @Description: Implement add, minus, multiply and divide only using bit manipulation
 *      https://leetcode.com/problems/divide-two-integers
 * @Note:
 */
public class Code19_BitAddMinusMultiDiv {

    public static int add(int a, int b) {
        int sumNoCarry;
        int carry;
        while (b != 0) {
            sumNoCarry = a ^ b;  // get the sum of a and b without carry
            carry = (a & b) << 1;  // get the carry
            a = sumNoCarry;
            b = carry;
        }
        return a;
    }

    public int negate(int a) {
        return add(~a, 1);
    }

    public int minus(int a, int b) {
        return add(a, negate(b));
    }

    public int multiply(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {  // if the lowest digit of b is 1
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }

    public boolean isNegative(int a) {
        return a < 0;
    }

    public int divExceptMin(int a, int b) {
        // convert both a and b to be positive
        int x = isNegative(a) ? negate(a) : a;
        int y = isNegative(b) ? negate(b) : b;
        int res = 0;
        for (int i = 30; i >= 0; i--) {
            if ((x >> i) >= y) {  // when the biggest digits of a and y are nearest, meanwhile x >= y
                res |= 1 << i;  // then this digit will be 1
                x = minus(x, y << i);  // remaining of x continues
            }
        }
        return isNegative(a) ^ isNegative(b) ? negate(res) : res;
    }

    public int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            if (b == negate(1)) {  // Integer.MIN_VALUE / -1 = Integer.MAX_VALUE
                return Integer.MAX_VALUE;
            } else {  // split a into two parts
                int c = divExceptMin(add(a, 1), b);
                return add(c, divExceptMin(minus(a, multiply(c, b)), b));
            }
        } else {
            return divExceptMin(a, b);
        }
    }

}
