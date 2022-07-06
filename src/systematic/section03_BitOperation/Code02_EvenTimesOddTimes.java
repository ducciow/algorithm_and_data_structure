package systematic.section03_BitOperation;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: In a given array, there is one value appears odd times, and any other value appears even times. Find
 *      and print the odd-time value.
 * @Note:   XOR all items in the given array.
 */
public class Code02_EvenTimesOddTimes {

    public static int findOdd(int[] arr) {
        int xor = 0;
        for (int num : arr) {
            xor ^= num;
        }
        return xor;
    }


    public static int[] generateArray(int maxHalfLen, int maxVal) {
        int N = ((int) (Math.random() * maxHalfLen) + 1) * 2 + 1;
        int[] arr = new int[N];
        int oddTime = ((int) (Math.random() * (N / 10))) * 2 + 1;
        int oddVal = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
        int i = 0;
        for (; i < oddTime; i++) {
            arr[i] = oddVal;
        }
        HashSet<Integer> set = new HashSet<>();
        set.add(oddVal);
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

    public static boolean check(int[] arr, int odd) {
        int oddTime = 0;
        for (int num : arr) {
            if (num == odd) {
                oddTime++;
            }
        }
        return (oddTime & 1) != 0;
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
            int odd = findOdd(arr);
            if (!check(arr, odd)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
