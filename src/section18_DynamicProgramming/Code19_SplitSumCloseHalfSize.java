package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 02, 05, 2022
 * @Description: Given an array of integers, split it into two so that their size are most equal and their sums are most
 *      close. Return the smaller sum of array.
 * @Note:   Ver1. brute force.
 *          Ver2. DP.
 *          ======
 *          - The base case of brute force might not be convenient or even legitimate enough for dp.
 *          - Think about boundaries.
 */
public class Code19_SplitSumCloseHalfSize {

    public static int split1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        int N = arr.length;
        if ((N & 1) == 0) {
            return process1(arr, 0, N / 2, sum / 2);
        } else {
            return Math.max(process1(arr, 0, N / 2, sum / 2),
                    process1(arr, 0, N / 2 + 1, sum / 2));
        }
    }

    private static int process1(int[] arr, int idx, int size, int sum) {
//        if (idx > arr.length - size) {
//            return -1;
//        }
//        if (size == 0) {
//            return 0;
//        }
        if (idx == arr.length) {
            return size == 0 ? 0 : -1;  // use -1 to indicate invalid size
        }
        int p1 = process1(arr, idx + 1, size, sum);
        int next = arr[idx] <= sum ? process1(arr, idx + 1, size - 1, sum - arr[idx]) : -1;
        int p2 = next != -1 ? arr[idx] + next : -1;  // propagate -1
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
        sum /= 2;
        int N = arr.length;
        int M = (N + 1) / 2;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        // set all cells to -1 beforehand, partial base case
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        // rest base case
        for (int k = 0; k <= sum; k++) {
            dp[N][0][k] = 0;
        }
        // filling in
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    int p1 = dp[i + 1][j][k];
                    int next = arr[i] <= k && j - 1 >= 0 ? dp[i + 1][j - 1][k - arr[i]] : -1;
                    int p2 = next != -1 ? arr[i] + next : -1;
                    dp[i][j][k] = Math.max(p1, p2);
                }
            }
        }
        // return
        if ((N & 1) == 0) {
            return dp[0][N / 2][sum];
        } else {
            return Math.max(dp[0][N / 2][sum], dp[0][M][sum]);
        }
    }


    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = split1(arr);
            int ans2 = split2(arr);
            if (ans1 != ans2) {
                System.out.println("Failed");
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
