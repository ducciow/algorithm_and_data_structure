package systematic.section05_MergeSort;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 31, 03, 2022
 * @Description: Merge sort: recursion version (top-down)
 *                           iteration version (bottom-up)
 * @Note:   For iteration version, be careful about the possible index overflow:
 *              1. check before doubling step size
 *              2. check before compute M, resulting in L being checked by the way
 *              3. use M + min() for computing R
 */
public class Code01_MergeSort {
    public static void main(String[] args) {
        validate();
    }

    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    private static void merge(int[] arr, int L, int M, int R) {
        int[] tmp = new int[R - L + 1];
        int i = 0;
        int idx1 = L;
        int idx2 = M + 1;
        while (idx1 <= M && idx2 <= R) {
            tmp[i++] = arr[idx1] <= arr[idx2] ? arr[idx1++] : arr[idx2++];
        }
        while (idx1 <= M) {
            tmp[i++] = arr[idx1++];
        }
        while (idx2 <= R) {
            tmp[i++] = arr[idx2++];
        }
        for (int j = 0; j < L - R + 1; j++) {
            arr[L + j] = tmp[j];
        }
    }

    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        int step = 1;
        while (step < N) {
            int L = 0;
            while (L < N) {
                if (L > N - 1 - step) {
                    break;
                }
                int M = L + step - 1;
                int R = M + Math.min(N - 1 - M, step);
                merge(arr, L, M, R);
                L = R + 1;
            }

            if (step > N - step) {
                break;
            }
            step <<= 1;
        }
    }

    public static int[] generateArray(int maxLen, int maxVal) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxLen + 1)) - (int) (Math.random() * (maxLen + 1));
        }
        return arr;
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

    public static void validate() {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr1 = generateArray(maxL, maxV);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case:" + Arrays.toString(arr1));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
