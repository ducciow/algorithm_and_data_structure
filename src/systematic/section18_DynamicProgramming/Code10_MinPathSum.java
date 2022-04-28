package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 28, 04, 2022
 * @Description: Given a table of non-negative integers, return the minimum path from top-left to bottom-right corner.
 * @Note:   Ver1. DP.
 *          Ver2. DP with space compression: Use a 1-d array to replace the 2-d dp table.
 */
public class Code10_MinPathSum {

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] W = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(W));
        System.out.println(minPathSum2(W));
    }

    public static int minPathSum1(int[][] W) {
        if (W == null || W.length == 0 || W[0] == null || W[0].length == 0) {
            return 0;
        }
        int M = W.length;
        int N = W[0].length;
        int[][] dp = new int[M][N];
        dp[0][0] = W[0][0];
        for (int j = 1; j < N; j++) {
            dp[0][j] = dp[0][j - 1] + W[0][j];
        }
        for (int i = 1; i < M; i++) {
            dp[i][0] = dp[i - 1][0] + W[i][0];
            for (int j = 1; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + W[i][j];
            }
        }
        return dp[M - 1][N - 1];
    }

    public static int minPathSum2(int[][] W) {
        if (W == null || W.length == 0 || W[0] == null || W[0].length == 0) {
            return 0;
        }
        int M = W.length;
        int N = W[0].length;
        int[] arr = new int[N];
        arr[0] = W[0][0];
        for (int j = 1; j < N; j++) {
            arr[j] = arr[j - 1] + W[0][j];
        }
        for (int i = 1; i < M; i++) {
            arr[0] = arr[0] + W[i][0];
            for (int j = 1; j < N; j++) {
                arr[j] = Math.min(arr[j - 1], arr[j]) + W[i][j];
            }
        }
        return arr[N - 1];
    }


    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

}
