package primary.class03;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 19, 03, 2022
 * @Description: Binary searching an array for one local minimum
 * @Note:   1. The given array is unsorted, but adjacency items must be different
 *          2. By the given condition, the local minimum must exit
 */
public class Code10_BSLocalMin {
    public static void main(String[] args) {
        validate();
    }

    public static int findLocalMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1) {
            return 0;
        }
        int N = arr.length;
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return N - 1;
        }
        int L = 0;
        int R = N - 1;
        while (L < R - 1) {  // there are at least three items
            int mid = L + ((R - L) >> 1);
            if (arr[mid - 1] > arr[mid] && arr[mid + 1] > arr[mid]) {
                return mid;
            }
            if (arr[mid - 1] < arr[mid]) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        // two items left
        return arr[L] < arr[R] ? L : R;
    }

    // make sure that adjacency values are different
    public static int[] genRandArr(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen);
        int[] res = new int[len];
        if (len > 0) {
            res[0] = (int) (Math.random() * maxVal);
            for (int i = 1; i < len; i++) {
                do {
                    res[i] = (int) (Math.random() * maxVal);
                } while (res[i] == res[i - 1]);
            }
        }
        return res;
    }

    public static boolean check(int[] arr, int idx) {
        if (arr == null || arr.length == 0) {
            return idx == -1;
        } else {
            int left = idx - 1;
            int right = idx + 1;
            boolean leftBigger = left < 0 || arr[left] > arr[idx];
            boolean rightBigger = right > arr.length - 1 || arr[right] > arr[idx];
            return leftBigger && rightBigger;
        }
    }

    public static void validate() {
        int numTest = 10000;
        int maxLen = 50;
        int maxVal = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxLen, maxVal);
            int target = (int) (Math.random() * maxVal);
            int idx = findLocalMin(arr);
            if (!check(arr, idx)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                System.out.println(target);
                System.out.println(idx);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
