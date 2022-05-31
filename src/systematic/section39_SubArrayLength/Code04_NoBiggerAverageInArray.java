package systematic.section39_SubArrayLength;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Given an integer array and an integer k, find the longest subarray whose average is less than or
 *      equal to k, and return its length.
 * @Note:   Can be converted to previous problem.
 */
public class Code04_NoBiggerAverageInArray {

    public static int getAvgMaxLen(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= v;
        }
        return getMaxLen(arr, 0);
    }

    private static int getMaxLen(int[] arr, int k) {
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        int toCover = 0;
        int sum = 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            while (toCover < arr.length && sum + minSums[toCover] <= k) {
                sum += minSums[toCover];
                toCover = minSumEnds[toCover] + 1;
            }
            len = Math.max(len, toCover - i);
            if (toCover > i) {
                sum -= arr[i];
            } else {
                toCover = i + 1;
            }
        }
        return len;
    }


    public static int naive(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (isValid(arr, i, j, k)) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    private static boolean isValid(int[] arr, int L, int R, int k) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return (double) sum / (double) (R - L + 1) <= k;
    }


    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxLen = 20;
        int maxValue = 100;
        System.out.println("Test begin ...");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int ans1 = getAvgMaxLen(arr1, value);
            int ans2 = naive(arr2, value);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
