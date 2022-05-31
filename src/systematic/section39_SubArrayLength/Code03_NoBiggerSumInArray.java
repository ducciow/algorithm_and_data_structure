package systematic.section39_SubArrayLength;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Given an integer array, and an integer k, find the longest subarray whose sum is equal to or less than
 *      k, and return its length.
 * @Note:   1. Key idea: drop possibilities that are definitely not the desired answer, ie., we only need the longest
 *             subarray, so any subarray shorter than the current best is not in consideration.
 *          2. Also use auxiliary structures.
 */
public class Code03_NoBiggerSumInArray {

    public static int getMaxLen(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] minSum = new int[N];
        int[] minSumEnd = new int[N];
        minSum[N - 1] = arr[N - 1];
        minSumEnd[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            if (minSum[i + 1] < 0) {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            } else {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }
        int next = 0;
        int sum = 0;
        int len = 0;
        for (int i = 0; i < N; i++) {
            while (next < N && sum + minSum[next] <= k) {
                sum += minSum[next];
                next = minSumEnd[next] + 1;
            }
            len = Math.max(len, next - i);
            if (next > i) {
                sum -= arr[i];
            } else {
                next = i + 1;
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
        return sum <= k;
    }

    public static int[] generateRandomArray(int size, int value) {
        int[] ans = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int len = 50;
        int value = 100;
        System.out.println("Test begin ...");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = getMaxLen(arr, K);
            int ans2 = naive(arr, K);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");

    }

}
