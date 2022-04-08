package preheat;

import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 22, 03, 2022
 * @Description: A map of integers for storing numbers, where the digits of integers indicate the occurrences of
 *      according numbers
 * @Note:   1L
 */
public class Code18_BitMap {
    public static void main(String[] args) {
        validate();
    }

    public static class BitMap {
        long[] bits;

        public BitMap(int max) {
            bits = new long[(max >> 6) + 1];
        }

        public void add(int num) {
            // num >> 6: num / 64, find the integer
            // num & 63: num % 64, find the digit
            // 1L << (num & 63): mark the digit to be the only 1
            // |=: change the digit of the integer to be 1
            bits[num >> 6] |= (1L << (num & 63));
        }

        public void remove(int num) {
            bits[num >> 6] &= ~(1L << (num & 63));
        }

        public boolean contains(int num) {
            return (bits[num >> 6] & (1L << (num & 63))) != 0;
        }
    }

    public static void validate() {
        int testTime = 10000000;
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.remove(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("Failed");
                    return;
                }
            }
        }
        for (int num = 0; num <= max; num++) {
            if (bitMap.contains(num) != set.contains(num)) {
                System.out.println("Failed");
            }
        }
        System.out.println("Passed!");
    }
}
