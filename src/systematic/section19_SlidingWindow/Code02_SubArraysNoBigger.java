package systematic.section19_SlidingWindow;

import java.util.LinkedList;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: Given an integer array and a target value, return the number of sub-arrays in which the difference
 *      between minimum and maximum elements is less or equal to the target value.
 * @Note:   1. Use two Deque's for storing the indices of current valid maximum and minimum elements.
 *          2. Fix L, and increase R until arr[L...R] is invalid, then the number of valid sub-arrays is R - L, meaning
 *             the sub-array with starting index L.
 *          3. Drop outdated elements in Deque.
 *          4. increase L by 1, and continue with increasing R, repeat.
 */
public class Code02_SubArraysNoBigger {

    public static void main(String[] args) {
        validate();
    }

    public static int subArrays(int[] arr, int target) {
        if (arr == null || arr.length < 1 || target < 0) {
            return 0;
        }
        int N = arr.length;
        LinkedList<Integer> max = new LinkedList<>();
        LinkedList<Integer> min = new LinkedList<>();
        int count = 0;
        int L = 0;
        int R = 0;
        while (L < N) {
            while (R < N) {
                while (!max.isEmpty() && arr[max.peekLast()] <= arr[R]) {
                    max.pollLast();
                }
                max.addLast(R);
                while (!min.isEmpty() && arr[min.peekLast()] >= arr[R]) {
                    min.pollLast();
                }
                min.addLast(R);
                if (arr[max.peekFirst()] - arr[min.peekFirst()] > target) {
                    break;
                } else {
                    R++;
                }
            }
            count += R - L;
            if (L == max.peekFirst()) {
                max.pollFirst();
            }
            if (L == min.peekFirst()) {
                min.pollFirst();
            }
            L++;
        }
        return count;
    }


    public static int naiveSubArrays(int[] arr, int target) {
        if (arr == null || arr.length < 1 || target < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int k = L + 1; k <= R; k++) {
                    max = Math.max(max, arr[k]);
                    min = Math.min(min, arr[k]);
                }
                if (max - min <= target) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int[] genRandArr(int maxL, int maxV) {
        int[] arr = new int[(int) (Math.random() * (maxL + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxV + 1));
        }
        return arr;
    }

    public static void validate() {
        int numTest = 10000;
        int maxL = 20;
        int maxV = 100;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxL, maxV);
            int target = (int) (Math.random() * (maxV + 1));
            int ans1 = subArrays(arr, target);
            int ans2 = naiveSubArrays(arr, target);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
