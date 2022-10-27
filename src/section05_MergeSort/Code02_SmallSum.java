package section05_MergeSort;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 31, 03, 2022
 * @Description: Given an integer array, return its small sum, where a small sum means the sum of smaller values before
 *      every item, eg, {1, 4, 2, 8, 5, 7} -> 0 + 1 + 1 + 7 + 7 + 12 = 28.
 * @Note:   Collect sub-answers along merge:
 *              - The process() and merge() return int.
 *              - When merge(arr, L, M, R):
 *                  a) if arr[left] < arr[right], merge the left, and increase answer by arr[left] * (R - right + 1),
 *                     meaning the current left item is smaller than (R - right + 1) items to its right.
     *              b) if arr[left] >= arr[right], merge the right, letting the current left item continue to compare.
 */
public class Code02_SmallSum {

    public static int smallSum(int[] arr) {
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
        return ansL + ansR + merge(arr, L, mid, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int[] tmp = new int[R - L + 1];
        int i = 0;
        int idx1 = L;
        int idx2 = M + 1;
        int ans = 0;
        while (idx1 <= M && idx2 <= R) {
            if (arr[idx1] < arr[idx2]) {
                ans += arr[idx1] * (R - idx2 + 1);
                tmp[i++] = arr[idx1++];
            } else {
                tmp[i++] = arr[idx2++];
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

    public static int[] generateArray(int maxLen, int maxVal) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxLen + 1)) - (int) (Math.random() * (maxLen + 1));
        }
        return arr;
    }

    public static int naiveSmallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                ans += arr[j] < arr[i] ? arr[j] : 0;
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
            int ans1 = smallSum(arr1);
            int ans2 = naiveSmallSum(arr2);
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
