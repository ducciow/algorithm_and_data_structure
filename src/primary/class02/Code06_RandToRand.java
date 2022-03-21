package primary.class02;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 18, 03, 2022
 * @Description: Part1. Math.random() fundamentals
 *               Part2. Transform uniform distribution of integers [a, c] to [b, d]
 *               Part3. Transform fixed un-uniform binary distribution of 0 and 1, to uniform binary distribution
 * @Note:   1. Math.random() -> doubles in [0,1)
 *          2. (int) -> only keeps the integer part, regardless of sign
 */
public class Code06_RandToRand {
    public static void main(String[] args) {

        /*
        Part 1. Fundamentals
        */
        int numTest = 10000000;
        int count = 0;

        // show prob
        for (int i = 0; i < numTest; i++) {
            if (Math.random() < 0.75) {
                count++;
            }
        }
        System.out.println((double) count / (double) (numTest));

        // double [0, 8)
        System.out.println("==========");
        count = 0;
        int K = 8;
        for (int i = 0; i < numTest; i++) {
            if (Math.random() * K < 5) {
                count++;
            }
        }
        System.out.println((double) count / numTest);
        System.out.println((double) 5 / 8);

        // int [0, 8)
        System.out.println("==========");
        K = 9;
        int[] counts = new int[K];
        for (int i = 0; i < numTest; i++) {
            int res = (int) (Math.random() * K);
            counts[res]++;
        }
        System.out.println(Arrays.toString(counts));

        // p(x) to p(x**2)
        System.out.println("==========");
        count = 0;
        for (int i = 0; i < numTest; i++) {
            if (transX() < 0.75) {
                count++;
            }
        }
        System.out.println((double) count / numTest);
        System.out.println(Math.pow(0.75, 2));

        /*
        Part 2. [1, 5] to [1, 7]
        */
        System.out.println("==========");
        K = 7;
        counts = new int[K];
        for (int i = 0; i < numTest; i++) {
            int res = f3();
            counts[res - 1]++;
        }
        System.out.println(Arrays.toString(counts));

        /*
        Part 3. un-uniform binary distribution to uniform binary distribution
        */
        System.out.println("==========");
        K = 2;
        counts = new int[K];
        for (int i = 0; i < numTest; i++) {
            int res = g1();
            counts[res]++;
        }
        System.out.println(Arrays.toString(counts));
    }

    public static double transX() {
        // x to x**2
        return Math.max(Math.random(), Math.random());

        // x to 1 - (1 - x)**2
        //return Math.min(Math.random(), Math.random())
    }

    // given [1, 5]
    public static int f() {
        return (int) (Math.random() * 5) + 1;
    }

    // convert f() to binary distribution p(0) = p(1) = 0.5
    public static int f1() {
        int res;
        do {
            res = f();
        } while (res == 3);
        return res < 3 ? 0 : 1;
    }

    // convert f1() to [0, 6]
    public static int f2() {
        int res;
        do {
            res = (f1() << 2) + (f1() << 1) + f1();  // 3 digits cover [0, 7], then drop 7
        } while (res == 7);
        return res;
    }

    //  convert f2() to [1, 7]
    public static int f3() {
        return f2() + 1;
    }

    // given un-uniform binary distribution
    public static int g() {
        return Math.random() < 0.84 ? 0 : 1;
    }

    // convert g() to uniform binary distribution
    public static int g1() {
        int res;
        do {
            res = g();
        } while (res == g());
        return res;
    }
}
