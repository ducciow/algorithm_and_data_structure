package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 28, 04, 2022
 * @Description: There is a square of size M * N, and Bob starts on a given position. Bob can walk K steps in four
 *      directions, but if he walk outside the square, he will be dead. Return the probability of Bob being alive.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with an extra function checking boundaries.
 *          ======
 *          To get the probability, count number of valid ways, and divide by total number of cases.
 */
public class Code14_BobAlive {

    public static void main(String[] args) {
        System.out.println(bob1(50, 50, 6, 6, 10));
        System.out.println(bob2(50, 50, 6, 6, 10));
    }

    public static double bob1(int M, int N, int startX, int startY, int K) {
        return (double) process1(M, N, startX, startY, K) / Math.pow(4, K);
    }

    public static int process1(int M, int N, int x, int y, int K) {
        if (x < 0 || x >= M || y < 0 || y >= N) {
            return 0;
        }
        if (K == 0) {
            return 1;
        }
        int p1 = process1(M, N, x + 1, y, K - 1);
        int p2 = process1(M, N, x - 1, y, K - 1);
        int p3 = process1(M, N, x, y + 1, K - 1);
        int p4 = process1(M, N, x, y - 1, K - 1);
        return p1 + p2 + p3 + p4;
    }

    public static double bob2(int M, int N, int startX, int startY, int K) {
        int[][][] dp = new int[M][N][K + 1];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int k = 1; k <= K; k++) {
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    dp[i][j][k] += pick(M, N, i + 1, j, k - 1, dp);
                    dp[i][j][k] += pick(M, N, i - 1, j, k - 1, dp);
                    dp[i][j][k] += pick(M, N, i, j + 1, k - 1, dp);
                    dp[i][j][k] += pick(M, N, i, j - 1, k - 1, dp);
                }
            }
        }
        return (double) dp[startX][startY][K] / Math.pow(4, K);
    }

    public static int pick(int M, int N, int x, int y, int k, int[][][] dp) {
        if (x < 0 || x >= M || y < 0 || y >= N) {
            return 0;
        }
        return dp[x][y][k];
    }

}
