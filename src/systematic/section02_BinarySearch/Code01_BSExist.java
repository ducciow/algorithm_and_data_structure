package systematic.section02_BinarySearch;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Binary searching if a given sorted array has the target value
 * @Note:   1. Array must be sorted
 *          2. mid = L + ((R - L) >> 1) avoids overflow
 *          3. For loop condition: L <= R means there are at least one numbers
 *                                 L < R means at least two numbers, so needs one extra check after the loop
 */
public class Code01_BSExist {

    public static void main(String[] args) {
        validate();
    }

    public static boolean exists(int[] arr, int target) {
        if (arr == null) {
            return false;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return false;
    }

    public static int[] genRandArr(int maxLen, int maxVal) {
        int N = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * maxVal);
        }
        return arr;
    }

    public static boolean check(int[] arr, int target) {
        if (arr == null) {
            return false;
        }
        for (int n : arr) {
            if (n == target) {
                return true;
            }
        }
        return false;
    }

    private static void validate() {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        int target = (int) (Math.random() * (maxV + 1)) - (int) (Math.random() * maxV);
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxL, maxV);
            Arrays.sort(arr);
            if (exists(arr, target) != check(arr, target)) {
                System.out.println("Failed on case: " + Arrays.toString(arr) + "with target " + target);
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
