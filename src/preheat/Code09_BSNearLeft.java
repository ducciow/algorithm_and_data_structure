package preheat;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 19, 03, 2022
 * @Description: Binary searching a sorted array for the leftmost index so that arr[idx] >= target
 * @Note:   1. Keep binary search when mid value meets the condition
 *          2. Shrink the search space toward the right direction
 *          ======
 *          Array must be sorted
 */
public class Code09_BSNearLeft {
    public static void main(String[] args) {
        goValidate();
    }

    public static int leftMostNoSmaller(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        int idx = -1;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= target) {
                idx = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return idx;
    }

    public static int[] genRandArr(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen);
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = (int) (Math.random() * maxVal);
        }
        return res;
    }

    public static int naiveFind(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    public static void goValidate() {
        int numTest = 10000;
        int maxLen = 50;
        int maxVal = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxLen, maxVal);
            Arrays.sort(arr);
            int target = (int) (Math.random() * maxVal);
            int idx = leftMostNoSmaller(arr, target);
            int idx1 = naiveFind(arr, target);
            if (idx != idx1) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                System.out.println(target);
                System.out.println(idx);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
