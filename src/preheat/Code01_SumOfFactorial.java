package preheat;

/**
 * @Author: duccio
 * @Date: 17, 03, 2022
 * @Description: Return the sum of factorials up to N
 * @Note:   Bookkeeping the former factorial for efficiency
 */
public class Code01_SumOfFactorial {
    public static void main(String[] args) {
        System.out.println(sumOfFac(10));
    }

    public static int sumOfFac(int N) {
        int res = 0;
        int fac = 1;
        for (int i = 1; i <= N; i++) {
            fac *= i;
            res += fac;
        }
        return res;
    }
}
