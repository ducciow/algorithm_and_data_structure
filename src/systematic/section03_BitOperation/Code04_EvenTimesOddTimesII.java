package systematic.section03_BitOperation;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: In a given array, there are two values appear odd times, and any other value appears even times. Find
 *      and print the two odd-time values.
 * @Note:   1. XOR all items gives: odd_num_a ^ odd_num_b.
 *          2. Extract any digit 1 of XOR, meaning odd_num_a and odd_num_b are different on this digit.
 *          3. According to this digit, divide all items into two separate parts, and XOR again.
 */
public class Code04_EvenTimesOddTimesII {

    public static int[] findTwoOdd(int[] arr) {
        int[] ret = new int[2];
        int xor = 0;
        for (int num : arr) {
            xor ^= num;
        }
        int rightmostOne = xor & (~xor + 1);
        int xor1 = 0;
        for (int num : arr) {
            if ((num & rightmostOne) != 0) {
                xor1 ^= num;
            }
        }
        ret[0] = xor1;
        ret[1] = xor ^ xor1;
        return ret;
    }


    public static int[] generateArray(int maxHalfLen, int maxVal) {
        int N = ((int) (Math.random() * maxHalfLen) + 1) * 2;
        int[] arr = new int[N];
        HashSet<Integer> set = new HashSet<>();
        int oddTime1 = ((int) (Math.random() * (N / 10))) * 2 + 1;
        int oddVal1 = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
        int i = 0;
        for (; i < oddTime1; i++) {
            arr[i] = oddVal1;
        }
        set.add(oddVal1);
        int oddTime2 = ((int) (Math.random() * (N / 10))) * 2 + 1;
        int oddVal2;
        do {
            oddVal2 = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
        } while (oddVal2 == oddVal1);
        for (; i < oddTime1 + oddTime2; i++) {
            arr[i] = oddVal2;
        }
        set.add(oddVal2);
        while (i < N) {
            int evenTime = ((int) (Math.random() * (N / 10)) + 1) * 2;
            if (i > N - evenTime) {
                evenTime = N - i;
            }
            int evenVal;
            do {
                evenVal = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
            } while (set.contains(evenVal));
            set.add(evenVal);
            int j = 0;
            for (; j < evenTime; j++) {
                arr[i + j] = evenVal;
            }
            i += j;
        }
        // make arr chaotic
        for (int k = 0; k < N; k++) {
            int j = (int) (Math.random() * N);
            swap(arr, k, j);
        }
        return arr;
    }

    public static boolean check(int[] arr, int[] odd) {
        int oddTime1 = 0;
        int oddTime2 = 0;
        for (int num : arr) {
            if (num == odd[0]) {
                oddTime1++;
            } else if (num == odd[1]) {
                oddTime2++;
            }
        }
        return (oddTime1 & 1) != 0 & (oddTime2 & 1) != 0;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxHalfLen = 100;
        int maxVal = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateArray(maxHalfLen, maxVal);
            int[] odd = findTwoOdd(arr);
            if (!check(arr, odd)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
