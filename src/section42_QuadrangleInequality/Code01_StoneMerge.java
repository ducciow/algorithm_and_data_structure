package section42_QuadrangleInequality;

/**
 * @Author: duccio
 * @Date: 04, 06, 2022
 * @Description: Given n piles of stones in a line, merge them into one pile. Each time you can only merge adjacent
 *      piles, and the total number of two piles of merged stones is the cost. Return the minimum total cost.
 * @Note:   Ver1. brute force.
 *          Ver2. dp with iteration for a cell.
 *          Ver3. dp without iteration, using quadrangle inequality.
 *
 */
public class Code01_StoneMerge {

    public static int[] getPreSums(int[] arr) {
        int N = arr.length;
        int[] preSums = new int[N + 1];
        for (int i = 0; i < N; i++) {
            preSums[i + 1] = preSums[i] + arr[i];
        }
        return preSums;
    }

    public static int min1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] preSums = getPreSums(arr);
        return process(0, arr.length - 1, preSums);
    }

    // get the min cost of stone merge within range [L, R]
    private static int process(int L, int R, int[] preSums) {
        if (L == R) {
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int split = L; split < R; split++) {
            next = Math.min(next, process(L, split, preSums) + process(split + 1, R, preSums));
        }
        return next + preSums[R + 1] - preSums[L];
    }

    public static int min2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] preSums = getPreSums(arr);
        int[][] dp = new int[N][N];
        // diagonal is 0
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                int next = Integer.MAX_VALUE;
                for (int split = L; split < R; split++) {
                    next = Math.min(next, dp[L][split] + dp[split + 1][R]);
                }
                dp[L][R] = next + preSums[R + 1] - preSums[L];
            }
        }
        return dp[0][N - 1];
    }

    public static int min3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] preSums = getPreSums(arr);
        int[][] dp = new int[N][N];
        int[][] best = new int[N][N];
        // diagonal is 0
        // the last second diagonal:
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = preSums[i + 2] - preSums[i];
            best[i][i + 1] = i;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                int lower = best[L][R - 1];
                int upper = best[L + 1][R];
                for (int split = lower; split <= upper; split++) {
                    int cur = dp[L][split] + dp[split + 1][R];
                    if (cur <= next) {
                        next = cur;
                        choose = split;
                    }
                }
                dp[L][R] = next + preSums[R + 1] - preSums[L];
                best[L][R] = choose;
            }
        }
        return dp[0][N - 1];
    }


    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000;
        int N = 15;
        int maxValue = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min2(arr);
            int ans2 = min3(arr);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
