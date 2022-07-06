package systematic.section02_BinarySearch;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Binary searching a sorted array for the rightmost index so that arr[idx] <= target.
 * @Note:   - Keep binary search when mid value meets the condition rather than return.
 *          - Shrink the search space toward the right direction.
 */
public class Code03_BSNearRight {

    public static int allRightBigger(int[] arr, int target) {
        if (arr == null) {
            return -1;
        }
        int ret = -1;
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] <= target) {
                ret = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ret;
    }


    public static int[] genRandArr(int maxLen, int maxVal) {
        int N = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * maxVal);
        }
        return arr;
    }

    public static int naiveFind(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        int target = (int) (Math.random() * (maxV + 1)) - (int) (Math.random() * maxV);
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxL, maxV);
            Arrays.sort(arr);
            int idx = allRightBigger(arr, target);
            int idx1 = naiveFind(arr, target);
            if (idx != idx1) {
                System.out.println("Failed on case: " + Arrays.toString(arr) + " with target " + target);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
