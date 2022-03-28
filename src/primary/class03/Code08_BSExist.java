package primary.class03;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 19, 03, 2022
 * @Description: Binary searching a sorted array for the index of a given value
 * @Note:   Array in this case must be sorted
 */
public class Code08_BSExist {
    public static void main(String[] args) {
        validate();
    }

    public static int find(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return -1;
    }

    public static int[] genRandArr(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen);
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = (int) (Math.random() * maxVal);
        }
        return res;
    }

    public static boolean check(int[] arr, int target, int idx) {
        if (idx != -1) {
            return arr[idx] == target;
        }
        for (int i : arr) {
            if (i == target) {
                return false;
            }
        }
        return true;
    }

    public static void validate() {
        int numTest = 10000;
        int maxLen = 50;
        int maxVal = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxLen, maxVal);
            Arrays.sort(arr);
            int target = (int) (Math.random() * maxVal);
            int idx = find(arr, target);
            if (!check(arr, target, idx)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                System.out.println(target);
                System.out.println(idx);
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
