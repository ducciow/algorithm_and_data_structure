package systematic.section03_BitOperation;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: In a given array, there is one value appears K times, and any other value appears M times, where M > 1
 *      and K < M. Find the K-time value, requiring time O(N), extra space O(1).
 * @Note:   1. Accumulate the occurrences of each digit of every item in an arr[32].
 *          2. If arr[i] % M == K, then the K-time value must have a 1 in this i-th digit.
 */
public class Code05_KTimesMTimes {
    public static void main(String[] args) {
        validate();
    }

    public static int findKinM(int[] arr, int K, int M) {
        int[] digitOccurrence = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                digitOccurrence[i] += 1 & (num >> i);
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (digitOccurrence[i] % M == K) {
                ans |= 1 << i;
            }
        }
        return ans;
    }

    public static int[] generateArray(int K, int M, int kindsOfM, int maxVal) {
        int kindsM = (int) (Math.random() * kindsOfM) + 1;
        int N = kindsM * M + K;
        int[] arr = new int[N];
        int valueK = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
        for (int i = 0; i < K; i++) {
            arr[i] = valueK;
        }
        HashSet<Integer> set = new HashSet<>();
        set.add(valueK);
        for (int j = 0; j < kindsM; j++) {
            int valueM;
            do {
                valueM = ((int) (Math.random() * maxVal) + 1) - ((int) (Math.random() * maxVal) + 1);
            } while (set.contains(valueM));
            for (int m = 0; m < M; m++) {
                arr[K + j * M + m] = valueM;
            }
            set.add(valueM);
        }
        // make arr chaotic
        for (int k = 0; k < N; k++) {
            int j = (int) (Math.random() * N);
            swap(arr, k, j);
        }
        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static boolean check(int[] arr, int K, int valueK) {
        int occurrence = 0;
        for (int num : arr) {
            if (num == valueK) {
                occurrence++;
            }
        }
        return occurrence == K;
    }

    public static void validate() {
        int numTest = 10000;
        int kindsOfM = 20;
        int maxVal = 200;
        int maxM = 10;
        for (int i = 0; i < numTest; i++) {
            int M = (int) (Math.random() * (maxM - 1)) + 2;
            int K = (int) (Math.random() * (M - 1)) + 1;
            int[] arr = generateArray(K, M, kindsOfM, maxVal);
            int valK = findKinM(arr, K, M);
            if (!check(arr, K, valK)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
