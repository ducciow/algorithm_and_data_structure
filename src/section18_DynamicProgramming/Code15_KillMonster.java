package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 29, 04, 2022
 * @Description: A monster has M hp, and a soldier will hit the monster for K times, each time deducting randomly
 *      0 to N hp. Return the probability of the soldier killing the monster.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with loop for a cell.
 *          Ver3. DP without loop for a cell.
 *          Ver4. Compute the number of cases where the monster ending up with being alive.
 *          ======
 *          - Sample correspondence based on respective positions.
 *          - The ordering of base cases matters.
 *          - When count the number of cases where the monster will be killed, pruning directly returning 0 is wrong.
 *            Instead, pruning should return the number of all following cases, ie., power(N+1, remaining_num_hit).
 *          - When optimizing the loop in dp, firstly make sure the dependent position exists in dp table, then make
 *            sure the compensation cell exists in dp table. In this problem, even the compensation cell is out of dp
 *            table, it also needs to perform compensation based on power(N+1, remaining_num_hit-1), because the
 *            pruning returnning is just as it !!!
 *          - For ver.4 that computes the alive cases, the compensation logic is different, where out-of-dp-table cells
 *            do not need to contribute further compensation !!!
 */
public class Code15_KillMonster {

    public static double kill1(int M, int N, int K) {
        if (M < 1 || N < 1 || K < 1) {
            return 0;
        }
        return (double) process1(M, N, K) / Math.pow(N + 1, K);
    }

    public static long process1(int hp, int damage, int times) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) {
            return (long) Math.pow(damage + 1, times);
        }
        int ans = 0;
        for (int dmg = 0; dmg <= damage; dmg++) {
            ans += process1(hp - dmg, damage, times - 1);
        }
        return ans;
    }

    public static double kill2(int M, int N, int K) {
        if (M < 1 || N < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][M + 1];
        dp[0][0] = 1;
        for (int k = 1; k <= K; k++) {
            dp[k][0] = (long) Math.pow(N + 1, k);
        }
        for (int times = 1; times <= K; times++) {
            for (int hp = 1; hp <= M; hp++) {
                for (int dmg = 0; dmg <= N; dmg++) {
                    if (hp - dmg >= 0) {
                        dp[times][hp] += dp[times - 1][hp - dmg];
                    } else {
                        dp[times][hp] += (long) Math.pow(N + 1, times - 1);
                    }
                }
            }
        }
        return (double) dp[K][M] / (long) Math.pow(N + 1, K);
    }

    public static double kill3(int M, int N, int K) {
        if (M < 1 || N < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][M + 1];
        dp[0][0] = 1;
        for (int k = 1; k <= K; k++) {
            dp[k][0] = (long) Math.pow(N + 1, k);
        }
        for (int times = 1; times <= K; times++) {
            for (int hp = 1; hp <= M; hp++) {
                dp[times][hp] = dp[times - 1][hp] + dp[times][hp - 1];
                if (hp - 1 - N >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - N];
                } else {
                    dp[times][hp] -= (long) Math.pow(N + 1, times - 1);
                }
            }
        }
        return (double) dp[K][M] / (long) Math.pow(N + 1, K);
    }

    public static double kill4(int M, int N, int K) {
        if (M < 1 || N < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][M + 1];
        /* base case:
        if (K == 0) {
            return M > 0 ? 1 : 0;
        }
        if (M <= 0) {
            return 0;
        } */
        for (int m = 1; m <= M; m++) {
            dp[0][m] = 1;
        }
//        for (int k = 0; k <= K; k++) {
//            dp[k][0] = 0;
//        }
        for (int times = 1; times <= K; times++) {
            for (int hp = 1; hp <= M; hp++) {
                dp[times][hp] = dp[times - 1][hp] + dp[times][hp - 1];
                if (hp - 1 - N >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - N];
                }
            }
        }
        return (double) ((long) Math.pow(N + 1, K) - dp[K][M]) / (long) Math.pow(N + 1, K);
    }


    public static void main(String[] args) {
        int MMax = 10;
        int NMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int M = (int) (Math.random() * MMax);
            int N = (int) (Math.random() * NMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = kill1(M, N, K);
            double ans2 = kill2(M, N, K);
            double ans3 = kill3(M, N, K);
            double ans4 = kill4(M, N, K);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
