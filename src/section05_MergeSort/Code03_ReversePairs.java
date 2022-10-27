package section05_MergeSort;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 31, 03, 2022
 * @Description: Given an integer array, count the number of pairs that are in reverse order, regardless of the
 *      distance between the pair, eg, {1, 4, 2, 8, 5, 7} -> 0 + 1 + 0 + 2 + 0 + 0 = 3.
 * @Note:   Sol.1. Similar to Code02_SmallSum, except when merge(), reverse the checking condition and calculate the
 *                 answer based on the remaining number of left group.
 *          Sol.2. Similarly, but merge backwards by merging the bigger one first.
 *          Sol.3. Add a separate operation during merge (see Code04_ReversePairsTwice).
 */
public class Code03_ReversePairs {

    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        int ansL = process(arr, L, mid);
        int ansR = process(arr, mid + 1, R);
        return ansL + ansR + merge2(arr, L, mid, R);
    }

    private static int merge1(int[] arr, int L, int M, int R) {
        int[] tmp = new int[R - L + 1];
        int i = 0;
        int idx1 = L;
        int idx2 = M + 1;
        int ans = 0;
        while (idx1 <= M && idx2 <= R) {
            if (arr[idx1] > arr[idx2]) {
                ans += M - idx1 + 1;  // meaning this many items are bigger than the current right item
                tmp[i++] = arr[idx2++];
            } else {
                tmp[i++] = arr[idx1++];
            }
        }
        while (idx1 <= M) {
            tmp[i++] = arr[idx1++];
        }
        while (idx2 <= R) {
            tmp[i++] = arr[idx2++];
        }
        for (int j = 0; j < tmp.length; j++) {
            arr[L + j] = tmp[j];
        }
        return ans;
    }

    private static int merge2(int[] arr, int L, int M, int R) {
        int[] tmp = new int[R - L + 1];
        int idx = tmp.length - 1;
        int idx1 = M;
        int idx2 = R;
        int ans = 0;
        while (idx1 >= L && idx2 > M) {
            if (arr[idx1] > arr[idx2]) {
                ans += idx2 - M;  // meaning the current left item is bigger than this many items in the right group
                tmp[idx--] = arr[idx1--];
            } else {
                tmp[idx--] = arr[idx2--];
            }
        }
        while (idx1 >= L) {
            tmp[idx--] = arr[idx1--];
        }
        while (idx2 > M) {
            tmp[idx--] = arr[idx2--];
        }
        for (int i = 0; i < tmp.length; i++) {
            arr[L + i] = tmp[i];
        }
        return ans;
    }


    public static int[] generateArray(int maxLen, int maxVal) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxLen + 1)) - (int) (Math.random() * (maxLen + 1));
        }
        return arr;
    }

    public static int naiveReversePair(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans += arr[j] < arr[i] ? 1 : 0;
            }
        }
        return ans;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null ^ arr2 == null) {
            return false;
        }
        if (arr1 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateArray(maxL, maxV);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            int ans1 = reversePairs(arr1);
            int ans2 = naiveReversePair(arr2);
            if (ans1 != ans2) {
                System.out.println("Failed on case:" + Arrays.toString(arr));
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
