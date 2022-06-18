package systematic.section02_BinarySearch;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Binary searching an array for one local minimum.
 * @Note:   1. By the given condition, the local minimum must exist.
 *          2. Check either the two ends meets the condition.
 *          3. By comparing the mid value to its neighbours, decide the next searching direction.
 *          ======
 *          - The given array is unsorted, but adjacency values must be different.
 *          - The loop condition decides what elements are left to get the final check.
 */
public class Code04_BSLocalMin {

    public static void main(String[] args) {
        validate();
    }

    public static int findLocalMin1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        if (N == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return N - 1;
        }
        int L = 0;
        int R = N - 1;
        while (L < R - 1) {  // means there must be at least three elements to consider
            int mid = L + ((R - L) >> 1);
            if (arr[mid - 1] < arr[mid]) {
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return arr[L] < arr[R] ? L : R;  // only arr[L] and arr[R] are left, so the smaller on is the answer
    }

    // a different view on boundaries
    public static int findLocalMin2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        if (N == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return N - 1;
        }
        int L = 1;  // <---
        int R = N - 2;  // <---
        while (L < R) {  // <---
            int mid = L + ((R - L) >> 1);
            if (arr[mid - 1] < arr[mid]) {
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return L;  // <--- only arr[L] is left, so it must be the local min
    }

    public static int[] genRandArr(int maxLen, int maxVal) {
        int N = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[N];
        if (N > 0) {
            arr[0] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * maxVal);
            for (int i = 1; i < N; i++) {
                do {
                    arr[i] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * maxVal);
                } while (arr[i] == arr[i - 1]);
            }
        }
        return arr;
    }

    public static boolean check(int[] arr, int idx) {
        if (idx == -1) {
            return arr == null || arr.length == 0;
        }
        boolean leftBigger = idx == 0 || arr[idx - 1] > arr[idx];
        boolean rightBigger = idx == arr.length - 1 || arr[idx + 1] > arr[idx];
        return leftBigger && rightBigger;
    }

    public static boolean isRight(int[] arr, int index) {
        if (arr.length <= 1) {
            return true;
        }
        if (index == 0) {
            return arr[index] < arr[index + 1];
        }
        if (index == arr.length - 1) {
            return arr[index] < arr[index - 1];
        }
        return arr[index] < arr[index - 1] && arr[index] < arr[index + 1];
    }

    private static void validate() {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxL, maxV);
            int idx = findLocalMin1(arr);
            if (!check(arr, idx)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                System.out.println("return idx " + idx);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
