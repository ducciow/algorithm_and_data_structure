package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 02, 05, 2022
 * @Description: Given an array of integers, split it into two so that their sums are most close. Return the smaller sum.
 * @Note:   Ver1. brute force.
 *          Ver2. DP.
 *          ======
 *          Similar to knapsack problem.
 */
public class Code18_SplitSumClose {

    public static void main(String[] args) {
        validate();
    }

    public static int split1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return process1(arr, 0, sum / 2);
    }

    public static int process1(int[] arr, int idx, int sum) {
        if (idx == arr.length) {
            return 0;
        }
        int p1 = process1(arr, idx + 1, sum);
        int p2 = 0;
        if (arr[idx] <= sum) {
            p2 = arr[idx] + process1(arr, idx + 1, sum - arr[idx]);
        }
        return Math.max(p1, p2);
    }

    public static int split2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        int N = arr.length;
        sum /= 2;
        int[][] dp = new int[N + 1][sum + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= sum; j++) {
                int p1 = dp[i + 1][j];
                int p2 = 0;
                if (arr[i] <= j) {
                    p2 = arr[i] + dp[i + 1][j - arr[i]];
                }
                dp[i][j] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }


    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void validate() {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = split1(arr);
            int ans2 = split2(arr);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
