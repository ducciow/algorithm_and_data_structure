package systematic.section42_QuadrangleInequality;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 05, 06, 2022
 * @Description: Given an integer array representing positions of houses, choose k positions to build post offices. To
 *      make the sum of distances of every house to its nearest post office smallest, return the smallest sum.
 * @Note:   Ver1. dp with iteration.
 *          Ver2. dp without iteration.
 *          ======
 *          1. Firstly solve the sub-problem: choose one position from array to build one post office so that the
 *             sum distance is smallest.
 *          2. Then for each 0~i positions and j post offices, split 0~i based on how many former positions j-1 post
 *             offices are in charge of.
 *          ======
 *          The ordering of filling in the dp table is important.
 */
public class Code03_PostOfficeProblem {

    public static int min1(int[] arr, int k) {
        if (arr == null || k < 1 || arr.length <= k) {
            return 0;
        }
        int N = arr.length;
        // sub-problem:
        int[][] w = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                w[L][R] = w[L][R - 1] + arr[R] - arr[L + (R - L) / 2];
            }
        }
        // solve the whole problem:
        // row: 0~i position    column: j post offices
        int[][] dp = new int[N][k + 1];
        // first row is 0
        // first column (k = 0) is unused
        // second column (k = 1) is the corresponding sub-solution
        for (int i = 1; i < N; i++) {
            dp[i][1] = w[0][i];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= Math.min(i, k); j++) {
                int next = Integer.MAX_VALUE;
                for (int split = 0; split <= i; split++) {
                    next = Math.min(next, dp[split][j - 1] + w[split + 1][i]);
                }
                dp[i][j] = next;
            }
        }
        return dp[N - 1][k];
    }

    public static int min2(int[] arr, int k) {
        if (arr == null || k < 1 || arr.length <= k) {
            return 0;
        }
        int N = arr.length;
        int[][] w = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                w[L][R] = w[L][R - 1] + arr[R] - arr[L + (R - L) / 2];
            }
        }
        int[][] dp = new int[N][k + 1];
        int[][] best = new int[N][k + 1];
        for (int i = 1; i < N; i++) {
            dp[i][1] = w[0][i];
            best[i][1] = -1;
        }
        for (int j = 2; j <= k; j++) {
            for (int i = N - 1; i >= j; i--) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                int lower = best[i][j - 1];
                int upper = i == N - 1 ? N - 1 : best[i + 1][j];
                for (int split = lower; split <= upper; split++) {
                    int distL = split == -1 ? 0 : dp[split][j - 1];
                    int distR = split == i ? 0 : w[split + 1][i];
                    int cur = distL + distR;
                    if (cur <= next) {
                        next = cur;
                        choose = split;
                    }
                }
                dp[i][j] = next;
                best[i][j] = choose;
            }
        }
        return dp[N - 1][k];
    }


    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int N = 30;
        int maxValue = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * N) + 1;
            int ans1 = min1(arr, num);
            int ans2 = min2(arr, num);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
